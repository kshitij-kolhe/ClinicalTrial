package com.kshitij.clinicaltrial.controller;


import com.kshitij.clinicaltrial.model.PatientInfo;
import com.kshitij.clinicaltrial.service.EnrollPatients;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RecruitmentController {

    private final EnrollPatients enrollPatients;

    @Autowired
    public RecruitmentController(final EnrollPatients enrollPatients) {
        this.enrollPatients = enrollPatients;
    }

    @PostMapping(value = "/recruit", consumes = MediaType.APPLICATION_JSON_VALUE)
    private void recruitPatientForClinicalTrials(@RequestBody final PatientInfo patient, final HttpServletResponse httpServletResponse) {

         if (enrollPatients.enrollForClinicalTrial(patient)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
         } else {
             httpServletResponse.setStatus(HttpServletResponse.SC_OK);
         }

    }

}
