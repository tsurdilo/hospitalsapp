package org.hospitals.hospitalsapp.data;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Doctor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String title;
    private int age;
    private String img;
    private List<String> specializations;
    private List<Patient> patients;

    public Doctor() {
    }

    public Doctor(String id,
                  String firstName,
                  String lastName,
                  String title,
                  int age,
                  String img,
                  List<String> specializations,
                  List<Patient> patients) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.age = age;
        this.img = img;
        this.specializations = specializations;
        this.patients = patients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<String> specializations) {
        this.specializations = specializations;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", age=" + age +
                ", img='" + img + '\'' +
                ", specializations=" + specializations +
                ", patients=" + patients +
                '}';
    }
}
