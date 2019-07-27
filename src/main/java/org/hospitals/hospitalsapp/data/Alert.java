package org.hospitals.hospitalsapp.data;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class Alert implements Serializable {

    @Id
    private String id;
    private String title;
    private String message;
    private String action;

    public Alert() {
    }

    public Alert(String id,
                 String title,
                 String message,
                 String action) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
