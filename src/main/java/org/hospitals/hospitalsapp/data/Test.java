package org.hospitals.hospitalsapp.data;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
    public String name;
    public boolean ongoing;
    public List<String> data;

    public Test() {

    }

    public Test(String name,
                boolean ongoing,
                List<String> data) {
        this.name = name;
        this.ongoing = ongoing;
        this.data = data;
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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}