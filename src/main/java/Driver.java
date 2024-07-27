import model.Doctor;
import model.Speciality;
import model.TimeSlot;
import service.DoctorAppService;
import service.impl.DoctorAppServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Driver {

    public static void main(String[] args) throws Exception {
        DoctorAppService doctorAppService = new DoctorAppServiceImpl();
        doctorAppService.registerPatient("don",22);
        Doctor doctor1 = new Doctor("DR Mohan", Speciality.DERMATOLOGIST);
        doctorAppService.registerDoctor(doctor1,"DERMATOLOGIST");
        List<TimeSlot> timeSlots = new ArrayList<>();
        //TimeSlot.
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date start = dateFormat.parse("10:30");
        Date end = dateFormat.parse("11:00");
        timeSlots.add( new TimeSlot(start,end,doctor1));
        Date start2 = dateFormat.parse("12:00");
        Date end2 = dateFormat.parse("12:30");
        System.out.println("-->"+start2);
        timeSlots.add( new TimeSlot(start2,end2,doctor1));
        doctorAppService.markDoctorAvailability("DR Mohan",timeSlots);

        Doctor doctor2 = new Doctor("DR Kevin", Speciality.DERMATOLOGIST);

        doctorAppService.registerDoctor(doctor2,"DERMATOLOGIST");
        List<TimeSlot> timeSlots2 = new ArrayList<>();
        Date start3= dateFormat.parse("09:00");
        Date end3 = dateFormat.parse("09:30");
        timeSlots2.add( new TimeSlot(start3,end3,doctor2));
        Date start4 = dateFormat.parse("11:00");
        Date end4 = dateFormat.parse("11:30");
        timeSlots2.add( new TimeSlot(start4,end4,doctor2));
        doctorAppService.markDoctorAvailability("DR Kevin",timeSlots2);
        doctorAppService.showAvailableAppointmentsBySpeciality("DERMATOLOGIST");
    }
}
