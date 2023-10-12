package com.kshitij.clinicaltrial.controller;



import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistry;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/patient/{id}")
    public Patient getPatient(@PathVariable("id") final int patientId, final HttpServletResponse httpServletResponse) {

        final Optional<Patient> patient = patientRegistry.getPatient(patientId);

        if (patient.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return patient.get();
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return null;
    }

}
