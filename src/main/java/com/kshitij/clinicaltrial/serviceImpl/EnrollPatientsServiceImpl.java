package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.model.PatientInfo;
import com.kshitij.clinicaltrial.service.EnrollPatientsService;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollPatientsServiceImpl implements EnrollPatientsService {

    private final PatientRegistryService patientRegistryService;

    @Autowired
    public EnrollPatientsServiceImpl(final PatientRegistryService patientRegistryService) {
        this.patientRegistryService = patientRegistryService;
    }

    @Override
    public boolean enrollForClinicalTrial(final PatientInfo patientInfo) {

        if (!isDuplicate(patientInfo)) {
            final Patient patient = new Patient(patientInfo.getName(), patientInfo.getAge(), patientInfo.getGender(), patientInfo.getMedicalCondition());

            patientRegistryService.addPatientToRegistry(patient);

            return true;
        }

        return false;
    }

    private boolean isDuplicate(final PatientInfo patientInfo) {

        for(Patient patient : patientRegistryService.getAllPatients()) {
            if (patientInfo.equals(patient)) {
                return true;
            }
        }
        return false;
    }
}
