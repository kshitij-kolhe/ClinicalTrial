package com.kshitij.clinicaltrial.model;


import java.util.Objects;

public class PatientInfo {
    private String name;
    private Integer age;
    private Gender gender;
    private String medicalCondition;
    public PatientInfo(final String name, final Integer age, final Gender gender, final String medicalCondition) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(final String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public boolean equals(Patient o) {
        return Objects.equals(name, o.getName()) && Objects.equals(age, o.getAge()) && gender == o.getGender() && Objects.equals(medicalCondition, o.getMedicalCondition());
    }

    @Override
    public String toString() {
        return "PatientInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", medicalCondition='" + medicalCondition + '\'' +
                '}';
    }
}
