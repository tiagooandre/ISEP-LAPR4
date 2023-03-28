package eapli.base.warehousemanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import eapli.framework.domain.model.AggregateRoot;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

@Entity
public class AGVDock implements AggregateRoot<String> {
    @Version
    private Long version;

    @Id
    private String id;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private String accessibility;

    @XmlElement
    @JsonProperty
    @OneToOne(fetch = FetchType.EAGER)
    private Coordinates begin;

    @XmlElement
    @JsonProperty
    @OneToOne(fetch = FetchType.EAGER)
    private Coordinates end;

    @XmlElement
    @JsonProperty
    @OneToOne(fetch = FetchType.EAGER)
    private Coordinates depth;

    public AGVDock(String id, String accessibility, Coordinates begin, Coordinates end, Coordinates depth) {
        this.id = id;
        this.accessibility = accessibility;
        this.begin = begin;
        this.end = end;
        this.depth = depth;
    }

    protected AGVDock() {
    }

    public Long getVersion() {
        return version;
    }

    public String getId() {
        return id;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public Coordinates getBegin() {
        return begin;
    }

    public Coordinates getEnd() {
        return end;
    }

    public Coordinates getDepth() {
        return depth;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public String identity() {
        return this.id;
    }
}