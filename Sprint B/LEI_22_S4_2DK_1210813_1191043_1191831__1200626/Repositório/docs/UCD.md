@startuml
!theme lightgray
left to right direction
actor "Project Manager" as pm
actor "Sales Clerk" as sc
actor "Warehouse Employee" as we
actor "Software Product Client" as spc

  usecase "G002: Elaborate a Domain Model using DDD." as G002
  usecase "G003: Configure the project repository." as G003
  usecase "G004: Configure the project structure to facilitate/accelerate the development of upcoming user stories." as G004
  usecase "US1001: Specify a new product for sale." as US1001
  usecase "US1002: View/search the products catalog." as US1002
  usecase "US1003: Register a new customer." as US1003
  usecase "US1004: Create a new products order on behalf of a given customer." as US1004
  usecase "US2001: Set up the warehouse plant by uploading a JSON file." as US2001
  usecase "US9001: Prepare a presentation." as US9001
  usecase "US1900: The system has the possibility of being initialized (bootstrap) with some information related to the product catalog and auxiliary information." as US1900
  usecase "US2002: Configure the AGVs available on the warehouse." as US2002
  usecase "US1005: Define a new category of products." as US1005

pm --> G002
pm --> G003
pm --> G004
pm --> US1900

sc --> US1001
sc --> US1002
sc --> US1003
sc --> US1004
sc --> US1005

we --> US2001
we --> US2002

spc --> US9001



@enduml