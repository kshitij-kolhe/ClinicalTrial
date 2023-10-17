package com.kshitij.clinicaltrial.service;

import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;

import java.util.Optional;

public interface PatientModifyService {

    boolean deletePatientFromRegistry(final int patientId);

    Optional<Patient> updatePatientInfo(final int patientId, final String name, final Integer age, final Gender gender, final String medicalCondition);

}
