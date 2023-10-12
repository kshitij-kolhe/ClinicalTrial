package com.kshitij.clinicaltrial.service;

import com.kshitij.clinicaltrial.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRegistry {

    void addPatientToRegistry(final Patient patient);

    List<Patient> getAllPatients();

    Optional<Patient> getPatient(final Integer id);
}
