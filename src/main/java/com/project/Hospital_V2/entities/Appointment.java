package com.project.Hospital_V2.entities;

import com.project.Hospital_V2.enums.KindOfReview;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.Date;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotNull
    @Valid
    private Time time;
    @NotNull
    @Valid
    private Date date;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    @NotNull
    private KindOfReview kindOfReview;

    public Appointment(Integer id, Time time, Date date, Patient patient, Doctor doctor, KindOfReview kindOfReview) {
        this.id = id;
        this.time = time;
        this.date = date;
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
