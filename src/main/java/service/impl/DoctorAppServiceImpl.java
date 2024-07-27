package service.impl;

import model.Appointment;
import model.Doctor;
import model.Patient;
import model.Speciality;
import model.TimeSlot;
import service.DoctorAppService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DoctorAppServiceImpl implements DoctorAppService {

    private final List<Appointment> appointmentList = new ArrayList<>();
    private final List<Doctor> registerdDoctorList = new ArrayList<>();
    private Map<String, Doctor> doctorMap = new HashMap<>();
    private Map<Speciality, List<Doctor>> specialityDoctorMap = new HashMap<>();
    private final Map<Integer, Appointment> bookingIdMap = new HashMap<>();
    private int bookingId=0;

    private final Map<String, Patient> patientMap = new HashMap<>();
    @Override
    public void registerDoctor(Doctor doctor, String speciality) {
        if(!Speciality.contains(speciality)){
            System.out.println("Speciality type is unknown!");
            return;
        }
        //Doctor doctor = new Doctor(doctorName,Speciality.valueOf(speciality));
        doctorMap.put(doctor.getName().toLowerCase(),doctor);
        if(!specialityDoctorMap.containsKey(Speciality.valueOf(speciality))){
            specialityDoctorMap.put(Speciality.valueOf(speciality),new ArrayList<>());
        }
        specialityDoctorMap.get(Speciality.valueOf(speciality)).add(doctor);
        registerdDoctorList.add(doctor);
        System.out.println("Rigistered Doctor!");
    }

    @Override
    public void registerPatient(String patientName, int age){
        Patient patient = new Patient(patientName,age);
        patientMap.put(patientName,patient);
        System.out.println("Rigistered Patient!");
    }

    @Override
    public void markDoctorAvailability(String doctorName, List<TimeSlot> timeslots) throws Exception {
         Doctor doctor = doctorMap.get(doctorName.toLowerCase());
         //9:30 - 10:30
         for(TimeSlot timeSlot : timeslots){
             if(timeSlot.getDuration()==30){
                 doctor.getTimeSlotList().add(timeSlot);
             } else{
                 throw new Exception("Slot is only for 30 mins!");
                 //System.out.println("Slot is only for 30 mins!");
             }
         }
        System.out.println("Added Doctor availability!");
    }

    @Override
    public int bookAppointment(String patientName, String doctorName, String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date timeSlot = dateFormat.parse(time);
        Doctor doctor = doctorMap.get(doctorName);
        Patient patient = patientMap.get(patientName);
        Appointment appointment = new Appointment(doctor,patient,timeSlot);
        int currentBookingId = -1;
        if(!hasAnyOtherAppointment(patient,appointment)) {
            currentBookingId = bookingId++;
            appointment.setId(currentBookingId);
            if(hasDoctorAppointAvailable(doctor,appointment)){
                doctor.addAppointment(appointment);
            } else{
                doctor.addAppointmentToWaitingList(appointment);
            }
            patient.addAppointment(appointment);
            appointmentList.add(appointment);
            bookingIdMap.put(bookingId, appointment);
        }
        return currentBookingId;
    }

    private boolean hasAnyOtherAppointment(Patient patient, Appointment appointment){
        if(patient.getAppointments().contains(appointment)){
            System.out.println("Already has same appointment!");
            return true;
        }
        Set<Appointment> appointments = patient.getAppointments();
        boolean anyPatientAppointmentFound = appointments.stream().allMatch(a -> a.getStartTime().equals(appointment.getStartTime())
                && !a.getDoctor().getName().equals(appointment.getDoctor().getName()));

        boolean anyAppointmentFound = appointmentList.stream().allMatch(a -> a.getStartTime().equals(appointment.getStartTime())
                && a.getDoctor().getName().equals(appointment.getDoctor().getName()));
        return anyPatientAppointmentFound && anyAppointmentFound;
    }

    boolean hasDoctorAppointAvailable(Doctor doctor, Appointment appointment){
        boolean anyAppointmentFound = doctor.getAppointments().stream().allMatch(a -> a.getStartTime().equals(appointment.getStartTime())
                && !a.getDoctor().getName().equals(appointment.getDoctor().getName()));
        return anyAppointmentFound;
    }

    @Override
    public void cancelAppointmentByBookingId(int bookingId) {
        if(bookingIdMap.containsKey(bookingId)){
            Appointment appointment = bookingIdMap.get(bookingId);
            Doctor doctor = appointment.getDoctor();
            doctor.getAppointments().remove(appointment);
            doctor.freeFromWaitingList(appointment);
            Patient patient = appointment.getPatient();
            patient.getAppointments().remove(appointment);
            appointmentList.remove(appointment);
            bookingIdMap.remove(bookingId);
        } else{
            System.out.println("Booking id not found!");
        }
    }

    @Override
    public void showAvailableAppointmentsBySpeciality(String speciality) {
        if(Speciality.contains(speciality)) {
            System.out.println("specialityDoctorMap:"+specialityDoctorMap);
            List<Doctor> doctors = specialityDoctorMap.get(Speciality.valueOf(speciality));
            System.out.println("---ss---"+doctors);
            if(doctors!=null && !doctors.isEmpty()){
                System.out.println("---ss---");
                print(speciality, doctors);
            }
        } else{
            System.out.println("No such Speciality type exists!");
        }
        System.out.println("---END---");
    }

    private void printAvailableDoctorsWithSpeciality(String speciality, List<Doctor> doctors){
        System.out.println(speciality + ":");
        Map<TimeSlot,Doctor> timeSlotDoctorMap = new HashMap<>();
        List<TimeSlot> timeSlots = new ArrayList<>();
        for(Doctor doctor : doctors){
            for(TimeSlot timeSlot : doctor.getTimeSlotList()){
                timeSlotDoctorMap.put(timeSlot,doctor);
            }
            timeSlots.addAll(doctor.getTimeSlotList());
        }
        System.out.println("timeSlotDoctorMap:"+timeSlotDoctorMap);
        Comparator<TimeSlot> timeSlotComparator = Comparator.comparing(a -> timeSlotDoctorMap.get(a).getName(),Comparator.reverseOrder());
        Comparator<TimeSlot> doctorRankComparator = Comparator.comparing(a -> timeSlotDoctorMap.get(a).getRating());
        // -> timeSlotDoctorMap.get(a.getStartTime()).getName().compareTo(timeSlotDoctorMap.get(b.getStartTime()).getName())
        Collections.sort(timeSlots, timeSlotComparator);
        for(TimeSlot timeslot : timeSlots){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            System.out.println(timeSlotDoctorMap.get(timeslot).getName()+": "+df.format(timeslot.getStartTime())+"-"+df.format(timeslot.getEndTime()));
        }
    }

    void print(String speciality, List<Doctor> doctors){
        System.out.println(speciality + ":");
        Comparator<TimeSlot> startTimeComparator =  Comparator.comparing(a->a.getStartTime());
        Comparator<TimeSlot> doctorRankComparator = Comparator.comparing(a -> a.getDoctor().getRating());

        List<TimeSlot> timeSlots = doctors.stream().flatMap(d->d.getTimeSlotList().stream()).sorted(startTimeComparator)
                .collect(Collectors.toList());
        for(TimeSlot timeslot : timeSlots){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            //System.out.println(timeslot.getDoctor().getName()+": ====>"+timeslot.getStartTime()+"-"+timeslot.getEndTime());

            System.out.println(timeslot.getDoctor().getName()+": "+df.format(timeslot.getStartTime())+"-"+df.format(timeslot.getEndTime()));
        }
    }
}
