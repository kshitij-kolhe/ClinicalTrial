package com.kshitij.clinicaltrial.controller;



import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InquiryController {

    private final PatientRegistry patientRegistry;

    @Autowired
    public InquiryController(final PatientRegistry patientRegistry) {
        this.patientRegistry = patientRegistry;
    }

    @GetMapping(value = "/patients")
    public List<Patient> getAllEnrolledPatients() {

        return patientRegistry.getAllPatients();
    }

}
