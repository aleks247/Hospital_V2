package com.project.Hospital_V2.entities;

import com.project.Hospital_V2.enums.KindOfReview;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    @NotNull
    private KindOfReview kindOfReview;

    public Appointment() {
    }

    public Appointment(LocalDateTime dateTime, Patient patient, Doctor doctor, KindOfReview kindOfReview) {
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
        this.kindOfReview = kindOfReview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public String getDateTime2() {
        String test = "Date: " + String.valueOf(dateTime);
        String test2 = test.replace("T", " Time: ");
        return test2;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public KindOfReview getKindOfReview() {
        return kindOfReview;
    }

    public void setKindOfReview(KindOfReview kindOfReview) {
        this.kindOfReview = kindOfReview;
    }
}
