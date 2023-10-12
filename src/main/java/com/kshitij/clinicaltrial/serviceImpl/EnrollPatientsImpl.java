package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.model.PatientInfo;
import com.kshitij.clinicaltrial.service.EnrollPatients;
import com.kshitij.clinicaltrial.service.PatientRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollPatientsImpl implements EnrollPatients {

    private final PatientRegistry patientRegistry;

    @Autowired
    public EnrollPatientsImpl(final PatientRegistry patientRegistry) {
        this.patientRegistry = patientRegistry;
    }

    @Override
    public boolean enrollForClinicalTrial(final PatientInfo patientInfo) {

        if (!isDuplicate(patientInfo)) {
            final Patient patient = new Patient(patientInfo.getName(), patientInfo.getAge(), patientInfo.getGender(), patientInfo.getMedicalCondition());

            patientRegistry.addPatientToRegistry(patient);

            return true;
        }

        return false;
    }

    private boolean isDuplicate(final PatientInfo patientInfo) {

        for(Patient patient : patientRegistry.getAllPatients()) {
            if (patientInfo.equals(patient)) {
                return true;
            }
        }
        return false;
    }
}
