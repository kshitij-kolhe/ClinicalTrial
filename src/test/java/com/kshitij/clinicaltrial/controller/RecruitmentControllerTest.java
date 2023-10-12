package com.kshitij.clinicaltrial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kshitij.clinicaltrial.model.PatientInfo;
import com.kshitij.clinicaltrial.service.EnrollPatients;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@WebMvcTest(controllers = RecruitmentController.class)
class RecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollPatients enrollPatients;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Recruit patient for clinical trial")
    public void testRecruitPatient_whenValidPatientDataPresented_returnCreatedResponse() throws Exception {
        final EasyRandom generator = new EasyRandom();

        final String requestBody = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(generator.nextObject(PatientInfo.class));

        RequestBuilder request = MockMvcRequestBuilders.post("/recruit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);

        when(enrollPatients.enrollForClinicalTrial(any(PatientInfo.class))).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(request).andReturn();

        Mockito.verify(enrollPatients, times(1)).enrollForClinicalTrial(any(PatientInfo.class));

        Assertions.assertEquals(201, mvcResult.getResponse().getStatus(), "a patient record should be created");
    }

    @Test
    @DisplayName("Do not recruit duplicate patient for clinical trial")
    public void testRecruitPatient_whenDuplicatePatientDataPresented_returnOKResponse() throws Exception {
        final EasyRandom generator = new EasyRandom();

        final String requestBody = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(generator.nextObject(PatientInfo.class));

        RequestBuilder request = MockMvcRequestBuilders.post("/recruit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);

        when(enrollPatients.enrollForClinicalTrial(any(PatientInfo.class))).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(request).andReturn();

        Mockito.verify(enrollPatients, times(1)).enrollForClinicalTrial(any(PatientInfo.class));

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus(), "a patient record should be created");
    }


}