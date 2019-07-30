package org.hospitals.hospitalsapp.data;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hospital implements Serializable {

    @Id
    private String id;
    private String name;
    private String desc;
    private String address;
    private String zip;
    private String img;
    private List<Doctor> doctors;

    public Hospital() {
    }

    public Hospital(String id,
                    String name,
                    String desc,
                    String address,
                    String zip,
                    String img,
                    List<Doctor> doctors) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.address = address;
        this.zip = zip;
        this.img = img;
        this.doctors = doctors;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", img='" + img + '\'' +
                ", doctors=" + doctors +
                '}';
    }
}