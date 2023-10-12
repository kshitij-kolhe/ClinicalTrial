package com.kshitij.clinicaltrial.model;

import java.time.LocalDate;
import java.util.Objects;

public class Patient {

    private Integer id;
    private String name;
    private Integer age;
    private Gender gender;
    private String medicalCondition;
    private LocalDate enrollDate;

    private static Integer enrolledPatients = 0;

    public Patient() {
    }

    public Patient(final String name, final Integer age, final Gender gender, final String medicalCondition) {
        Patient.enrolledPatients++;

        this.id = Patient.enrolledPatients;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.enrollDate = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public void setMedicalCondition(final String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(name, patient.name) && Objects.equals(age, patient.age) && gender == patient.gender && Objects.equals(medicalCondition, patient.medicalCondition) && Objects.equals(enrollDate, patient.enrollDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, medicalCondition, enrollDate);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", medicalCondition='" + medicalCondition + '\'' +
                ", enrollDate=" + enrollDate +
                '}';
    }
}
