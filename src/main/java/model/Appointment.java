package model;

import java.util.Date;
import java.util.Objects;

public class Appointment {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals(doctor, that.doctor) && Objects.equals(patient, that.patient) && Objects.equals(startTime, that.startTime) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor, patient, startTime, id);
    }

    private Doctor doctor;
    private Patient patient;
    private Date startTime;

    private Integer id;

    public Appointment(Doctor doctor, Patient patient, Date startTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.startTime = startTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
