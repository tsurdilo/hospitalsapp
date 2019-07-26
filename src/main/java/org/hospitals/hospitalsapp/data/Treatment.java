package org.hospitals.hospitalsapp.data;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Treatment implements Serializable {

    @Id
    private String id;
    private String name;
    private boolean ongoing;
    private String performedOn;
    private List<Test> tests;

    public Treatment() {

    }

    public Treatment(String id,
                     String name,
                     boolean ongoing,
                     String performedOn,
                     List<Test> tests) {
        this.name = name;
        this.ongoing = ongoing;
        this.performedOn = performedOn;
        this.tests = tests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public String getPerformedOn() {
        return performedOn;
    }

    public void setPerformedOn(String performedOn) {
        this.performedOn = performedOn;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ongoing=" + ongoing +
                ", performedOn='" + performedOn + '\'' +
                ", tests=" + tests +
                '}';
    }
}