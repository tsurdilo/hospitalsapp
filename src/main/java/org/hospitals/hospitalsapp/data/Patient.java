package org.hospitals.hospitalsapp.data;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Patient implements Serializable {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String zip;
    private List<Treatment> treatments;
    private Condition condition;

    public Patient() {

    }

    public Patient(String id,
                   String firstName,
                   String lastName,
                   String address,
                   String zip,
                   List<Treatment> treatments,
                   Condition condition) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.treatments = treatments;
        this.condition = condition;
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

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", treatments=" + treatments +
                ", condition=" + condition +
                '}';
    }
}