package model;

import javax.xml.crypto.Data;
import java.util.Date;

public class TimeSlot {

    private Date startTime;

    @Override
    public String toString() {
        return "TimeSlot{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                '}';
    }

    private Date endTime;
    private int duration;

    private Doctor doctor;

    public TimeSlot(Date startTime, Date endTime, Doctor doctor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor=doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return (int) ((endTime.getTime()-startTime.getTime())/(1000*60));
    }

//    public void setDuration(int duration) {
//        this.duration = duration;
//    }

}
