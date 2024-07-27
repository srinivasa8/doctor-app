package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Patient {

    public Patient(String name, int age) {
        this.name = name;
        this.age = age;
        this.appointments = new HashSet<>();
    }

    private String name;
    private int age;

    private Set<Appointment> appointments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointmentList) {
        this.appointments = appointmentList;
    }

    public void addAppointment(Appointment appointment) {
        if(this.appointments.isEmpty()) this.appointments = new HashSet<>();
        this.appointments.add(appointment);
    }

}
