package org.hospitals.hospitalsapp.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Treatment implements Serializable {
    private String name;
    private boolean isOngoing;
    private Date performedOn;
    private List<Test> tests;

    public Treatment() {

    }

    public Treatment(String name,
                     boolean isOngoing,
                     Date performedOn,
                     List<Test> tests) {
        this.name = name;
        this.isOngoing = isOngoing;
        this.performedOn = performedOn;
        this.tests = tests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    public Date getPerformedOn() {
        return performedOn;
    }

    public void setPerformedOn(Date performedOn) {
        this.performedOn = performedOn;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}