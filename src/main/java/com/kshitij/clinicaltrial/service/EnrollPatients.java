package com.kshitij.clinicaltrial.service;

import com.kshitij.clinicaltrial.model.PatientInfo;

public interface EnrollPatients {

    boolean enrollForClinicalTrial(final PatientInfo patientInfo);
}
