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
    private String enteredCondition;
    private Condition condition;
    private String hospitalId;
    private String notes;

    public Patient() {

    }

    public Patient(String id,
                   String firstName,
                   String lastName,
                   String address,
                   String zip,
                   List<Treatment> treatments,
                   String enteredCondition,
                   Condition condition,
                   String hospitalId,
                   String notes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.treatments = treatments;
        this.enteredCondition = enteredCondition;
        this.condition = condition;
        this.hospitalId = hospitalId;
        this.notes = notes;
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

    public String getEnteredCondition() {
        return enteredCondition;
    }

    public void setEnteredCondition(String enteredCondition) {
        this.enteredCondition = enteredCondition;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
                ", enteredCondition='" + enteredCondition + '\'' +
                ", condition=" + condition +
                ", hospitalId='" + hospitalId + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}