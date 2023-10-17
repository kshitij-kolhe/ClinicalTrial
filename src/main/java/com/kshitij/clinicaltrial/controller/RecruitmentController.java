package com.kshitij.clinicaltrial.controller;


import com.kshitij.clinicaltrial.model.PatientInfo;
import com.kshitij.clinicaltrial.service.EnrollPatientsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RecruitmentController {

    private final EnrollPatientsService enrollPatientsService;

    @Autowired
    public RecruitmentController(final EnrollPatientsService enrollPatientsService) {
        this.enrollPatientsService = enrollPatientsService;
    }

    @PostMapping(value = "/patient", consumes = MediaType.APPLICATION_JSON_VALUE)
    private void recruitPatientForClinicalTrials(@RequestBody final PatientInfo patient, final HttpServletResponse httpServletResponse) {

         if (enrollPatientsService.enrollForClinicalTrial(patient)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
         } else {
             httpServletResponse.setStatus(HttpServletResponse.SC_OK);
         }
    }

}
