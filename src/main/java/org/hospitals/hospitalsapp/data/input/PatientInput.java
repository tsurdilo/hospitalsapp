package org.hospitals.hospitalsapp.data.input;

import java.io.Serializable;

public class PatientInput implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private String zip;
    private String condition;
    private String hospitalid;
    private String notes;

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "PatientInput{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", condition='" + condition + '\'' +
                ", hospitalid='" + hospitalid + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
