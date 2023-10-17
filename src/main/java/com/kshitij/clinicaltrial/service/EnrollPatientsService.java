package com.kshitij.clinicaltrial.service;

import com.kshitij.clinicaltrial.model.PatientInfo;

public interface EnrollPatientsService {

    boolean enrollForClinicalTrial(final PatientInfo patientInfo);
}
