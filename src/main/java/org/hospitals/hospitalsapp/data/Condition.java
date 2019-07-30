package org.hospitals.hospitalsapp.data;

import java.io.Serializable;

public class Condition implements Serializable{
    private String name;
    private int stage;
    private int phase;
    private String notes;

    public Condition() {

    }

    public Condition(String name,
                     int stage,
                     int phase,
                     String notes) {
        this.name = name;
        this.stage = stage;
        this.phase = phase;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "name='" + name + '\'' +
                ", stage=" + stage +
                ", phase=" + phase +
                ", notes='" + notes + '\'' +
                '}';
    }
}
