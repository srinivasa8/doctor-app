package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Doctor {

    private String name;
    private Speciality speciality;
    private List<TimeSlot> timeSlotList;
    private Set<Appointment> appointments;
    private Map<Date, Queue<Appointment>> timeWaitingListMap;
    private int rating;

    public Doctor(String name, Speciality speciality) {
        this.name = name;
        this.speciality = speciality;
        this.timeWaitingListMap = new HashMap<>();
        this.timeSlotList = new ArrayList<>();
        this.appointments = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }

    public void setTimeSlotList(List<TimeSlot> timeSlotList) {
        this.timeSlotList = timeSlotList;
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

    public void addAppointmentToWaitingList(Appointment appointment) {
        Queue<Appointment> appointmentQueue = this.timeWaitingListMap.getOrDefault(appointment.getStartTime(), new LinkedList<>());
        appointmentQueue.add(appointment);
    }

    public void freeFromWaitingList(Appointment appointment){
        if(this.timeWaitingListMap.containsKey(appointment.getStartTime())){
            timeWaitingListMap.get(appointment.getStartTime()).poll();
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
