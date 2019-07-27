package org.hospitals.hospitalsapp.data;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Test implements Serializable {

    @Id
    private String id;
    public String name;
    public boolean ongoing;
    public List<String> data;

    public Test() {

    }

    public Test(String id,
                String name,
                boolean ongoing,
                List<String> data) {
        this.id = id;
        this.name = name;
        this.ongoing = ongoing;
        this.data = data;
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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ongoing=" + ongoing +
                ", data=" + data +
                '}';
    }
}