package service;

import model.Doctor;
import model.TimeSlot;

import java.text.ParseException;
import java.util.List;

public interface DoctorAppService {

    void registerDoctor(Doctor doctor, String speciality);
    void markDoctorAvailability(String doctor, List<TimeSlot> timeslots) throws Exception;
    void cancelAppointmentByBookingId(int bookingId);
    int bookAppointment(String patient, String doctor, String time) throws ParseException;
    void showAvailableAppointmentsBySpeciality(String speciality);
    void registerPatient(String patient, int age);
}
