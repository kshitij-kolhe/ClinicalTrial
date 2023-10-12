package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PatientRegistryImpl implements PatientRegistry {

    private static final List<Patient> patients = new ArrayList<>();

    @Override
    public void addPatientToRegistry(final Patient patient) {

        patients.add(patient);
    }


    @Override
    public List<Patient> getAllPatients() {
        return patients;
    }

    @Override
    public Optional<Patient> getPatient(Integer id) {
        return patients.stream()
                .filter(patient -> id.equals(patient.getId()))
                .findFirst();
    }


}
