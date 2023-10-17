package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PatientRegistryServiceImpl implements PatientRegistryService {

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

    @Override
    public boolean deletePatient(final Integer id) {

        return patients.removeIf(patient -> patient.getId().equals(id));
    }


}
