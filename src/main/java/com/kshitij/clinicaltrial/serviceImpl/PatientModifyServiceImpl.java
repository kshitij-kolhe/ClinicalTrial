package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientModifyService;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PatientModifyServiceImpl implements PatientModifyService {


    private final PatientRegistryService patientRegistryService;

    @Autowired
    public PatientModifyServiceImpl(final PatientRegistryService patientRegistryService) {
        this.patientRegistryService = patientRegistryService;
    }

    @Override
    public boolean deletePatientFromRegistry(int patientId) {

        return  patientRegistryService.deletePatient(patientId);
    }

    @Override
    public Optional<Patient> updatePatientInfo(final int patientId, final String name, final Integer age, final Gender gender, final String medicalCondition) {

        final Optional<Patient> patientOptional = patientRegistryService.getPatient(patientId);

        if (patientOptional.isPresent()) {
            final Patient patient = patientOptional.get();

            updatePatientName(patient, name);
            updatePatientAge(patient, age);
            updatePatientGender(patient, gender);
            updatePatientMedicalCondition(patient, medicalCondition);
        }

        return patientOptional;
    }

    private void updatePatientName(final Patient patient, final String name) {
        if (name != null) {
            patient.setName(name);
        }
    }
    private void updatePatientAge(final Patient patient, final Integer age) {
        if (age != null) {
            patient.setAge(age);
        }
    }
    private void updatePatientGender(final Patient patient, final Gender gender) {
        if (gender != null) {
            patient.setGender(gender);
        }
    }
    private void updatePatientMedicalCondition(final Patient patient, final String medicalCondition) {
        if (medicalCondition != null) {
            patient.setMedicalCondition(medicalCondition);
        }
    }
}
