package com.kshitij.clinicaltrial.controller;


import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientModifyService;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ModifierController {

    private final PatientModifyService patientModifyService;

    @Autowired
    public ModifierController(final PatientModifyService patientModifyService) {
        this.patientModifyService = patientModifyService;
    }

    @DeleteMapping(value = "/patient/{id}")
    public void removePatientFromTrial(@PathVariable("id") final int patientId, final HttpServletResponse httpServletResponse) {

        if (patientModifyService.deletePatientFromRegistry(patientId)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @PutMapping(value = "/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Patient updatePatientInfo(@PathVariable("id") final int patientId, @RequestParam(required = false) final String name
            , @RequestParam(required = false) final Integer age, @RequestParam(required = false) final Gender gender
            , @RequestParam(required = false) final String medicalCondition, final HttpServletResponse httpServletResponse) {

        final Optional<Patient> patient = patientModifyService.updatePatientInfo(patientId, name, age, gender, medicalCondition);

        if (patient.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return null;
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return patient.get();
    }

}
