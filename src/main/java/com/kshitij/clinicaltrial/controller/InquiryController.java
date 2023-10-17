package com.kshitij.clinicaltrial.controller;



import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InquiryController {

    private final PatientRegistryService patientRegistryService;

    @Autowired
    public InquiryController(final PatientRegistryService patientRegistryService) {
        this.patientRegistryService = patientRegistryService;
    }

    @GetMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Patient> getAllEnrolledPatients() {

        return patientRegistryService.getAllPatients();
    }

    @GetMapping(value = "/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Patient getPatient(@PathVariable("id") final int patientId, final HttpServletResponse httpServletResponse) {

        final Optional<Patient> patient = patientRegistryService.getPatient(patientId);

        if (patient.isPresent()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return patient.get();
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }

}
