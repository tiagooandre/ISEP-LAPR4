package domain;

import controller.*;
import utils.Constants;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.*;
import java.util.concurrent.Semaphore;

public class AGVManager {

    private final Map<Long, Thread> connections = new HashMap<>();
    private final Semaphore connectionsSemaphore = new Semaphore(88);

    private final List<Coordinates> taskList = new ArrayList<>();
    private final Semaphore taskListSemaphore = new Semaphore(88);

    private final JFrame frame = new JFrame("Warehouse Map");
    private final JButton[][] warehouseCells = new JButton[18][20];
    private final Semaphore warehouseCellsSemaphore = new Semaphore(88);
    private final int[][] warehouseTiles = new int[18][20];

    private final CoordinatesController coordinatesController = new CoordinatesController();
    private final DBUtilsController dbUtilsController = new DBUtilsController();
    private final PacketUtilsController packetUtilsController = new PacketUtilsController();
    private final SocketUtilsController socketUtilsController = new SocketUtilsController();
    private final UtilsController utilsController = new UtilsController();

    public void run() {
        new Thread(new UserInterface()).start();
        new Thread(new TaskManager()).start();
        new Thread(new ClientManager()).start();

        System.out.println("\nInitialized AGV Manager.\nWaiting for new connections.");
    }

    public void removeConnection(long connectionID, Coordinates currentPosition) {
        try {
            connectionsSemaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        connections.remove(connectionID);
        connectionsSemaphore.release();

        try {
            warehouseCellsSemaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setBackground(null);
        warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setText("");
        warehouseCellsSemaphore.release();
    }

    //TODO: Javadoc
    private class UserInterface implements Runnable {

        @Override
        public void run() {
            buildWarehouseMap();
        }

        //TODO: Javadoc
        private void buildWarehouseMap() {
            frame.setSize(1000, 890);
            frame.setLayout(new GridLayout(18, 20));

            for (int i = 0; i < Constants.warehouseMatrix.length; i++) {
                for (int j = 0; j < Constants.warehouseMatrix[0].length; j++) {
                    JButton jButton = new JButton();

                    if (Constants.warehouseMatrix[i][j] == 1) jButton.setBackground(Color.orange);
                    else if (Constants.warehouseMatrix[i][j] == 2) jButton.setBackground(Color.CYAN);

                    warehouseCells[i][j] = jButton;
                    frame.add(warehouseCells[i][j]);
                }
            }

            frame.setVisible(true);
        }
    }

    //TODO: Javadoc
    private class TaskManager implements Runnable {

        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                try {
                    taskListSemaphore.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                for (int i = 0; i < 10; i++) {
                    taskList.add(Constants.itemLocations[random.nextInt(Constants.itemLocations.length)]);
                }

                taskListSemaphore.release();
                utilsController.sleepSeconds(40);
            }
        }
    }

    //TODO: Javadoc
    private class ClientManager implements Runnable {

        @Override
        public void run() {
            System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\tezla\\Documents\\ISEP\\lei21_22_s4_2dk_03\\agvmanager\\src\\main\\resources\\serverKeyStore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");

            SSLServerSocket ss = socketUtilsController.createServerSocket(2223);

            while (true) {
                try {
                    SSLSocket clientSocket = (SSLSocket) ss.accept();
                    Thread newThread = new Thread(new TcpThread(clientSocket));

                    connections.put(newThread.getId(), newThread);
                    connections.get(newThread.getId()).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //TODO: Javadoc
    private class TcpThread implements Runnable {

        private final SSLSocket socket;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private Connection dbConnection;

        // AGV Related
        private String agvID;
        private Coordinates currentPosition;
        private String state = "free";

        public TcpThread(SSLSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("\nNew client connection - " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

            if (!performSetup()) {
                removeConnection(Thread.currentThread().getId(), currentPosition);
                return;
            }

            while (true) {
                while (!assignTask(output)) {
                    utilsController.sleepSeconds(3);
                }

                while (true) {
                    Packet packet = packetUtilsController.receivePacket(input);
                    if (packet == null) {
                        removeConnection(Thread.currentThread().getId(), currentPosition);
                        return;
                    }

                    if (packet.getCode() == ProtocolCodes.JOB_START.getCodeKey()) {
                        updateState("busy", dbConnection);
                    } else if (packet.getCode() == ProtocolCodes.JOB_UPDATE.getCodeKey()) {
                        updateCoordinates(new Coordinates().getObject(packet.getData()), dbConnection);
                    } else if (packet.getCode() == ProtocolCodes.JOB_COMPLETE.getCodeKey()) {
                        updateState("free", dbConnection);
                        break;
                    } else if (packet.getCode() == ProtocolCodes.AGV_CHARGING.getCodeKey()) {
                        updateState("charging", dbConnection);
                        utilsController.sleepSeconds(1);
                        break;
                    }
                }
            }
        }

        /**
         * Performs the initial setup for the AGV Manager thread.
         */
        private boolean performSetup() {
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());

                try {
                    dbConnection = dbUtilsController.createConnection();

                    try {
                        Packet packet = packetUtilsController.receivePacket(input);
                        agvID = new String(packet.getData(), StandardCharsets.UTF_8);

                        int baseLocationID;
                        String query1 = "SELECT BASELOCATION_ID FROM AGVDOCK WHERE AGVDOCK.ID = '" + agvID + "'";

                        try (Statement stmt = dbConnection.createStatement()) {
                            ResultSet rs = stmt.executeQuery(query1);
                            rs.next();
                            baseLocationID = rs.getInt("BASELOCATION_ID");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        String query2 = "SELECT LSQUARE, WSQUARE FROM COORDINATES WHERE COORDINATES.ID = " + baseLocationID;

                        try (Statement stmt = dbConnection.createStatement()) {
                            ResultSet rs = stmt.executeQuery(query2);
                            rs.next();
                            currentPosition = coordinatesController.createCoordinates(rs.getInt("LSQUARE"), rs.getInt("WSQUARE"));
                            packetUtilsController.sendPacket(ProtocolCodes.SETUP.getCodeKey(), currentPosition.getBytes(currentPosition), output);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        return true;
                    } catch (Exception e) {
                        System.err.println("Failed to acquire the ID of the AGV: " + e);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to connect to the database: " + e);
                }
            } catch (IOException e) {
                System.err.println("Failed to create I/O streams: " + e);
            }
            return false;
        }

        /**
         * Assigns a task to the AGV and checks if it received it.
         */
        private boolean assignTask(ObjectOutputStream output) {
            try {
                taskListSemaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (taskList.isEmpty()) {
                updateState("free", dbConnection);
                return false;
            }

            Coordinates task = taskList.remove(0);
            taskListSemaphore.release();

            packetUtilsController.sendPacket(ProtocolCodes.JOB_ASSIGN.getCodeKey(), task.getBytes(task), output);
            return true;
        }

        //TODO: Javadoc
        private void updateCoordinates(Coordinates coordinates, Connection connection) {
            warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setBackground(null);
            warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setText("");
            dbUtilsController.executeQuery("UPDATE AGVDOCK SET CURRENTPOSX = " + coordinates.getxPos() + ", CURRENTPOSY = " + coordinates.getyPos() + " WHERE ID = '" + agvID + "'", connection);
            currentPosition.setxPos(coordinates.getxPos());
            currentPosition.setyPos(coordinates.getyPos());
            warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setBackground(Color.red);
            warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setText(agvID);
        }

        //TODO: Javadoc
        private void updateState(String newState, Connection connection) {
            state = newState;

            switch (newState) {
                case "busy":
                    warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setBackground(Color.red);
                    break;
                case "free":
                    warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setBackground(Color.blue);
                    break;
                case "charging":
                    warehouseCells[currentPosition.getxPos()][currentPosition.getyPos()].setBackground(Color.green);
                    break;
            }

            dbUtilsController.executeQuery("UPDATE AGVDOCK SET STATE = '" + newState + "' WHERE ID = '" + agvID + "'", connection);
        }
    }
}