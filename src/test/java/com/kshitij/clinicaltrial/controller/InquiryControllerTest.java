package com.kshitij.clinicaltrial.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = InquiryController.class)
class InquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientRegistryService patientRegistryService;

    private ObjectMapper deserializer;
    private EasyRandom generator;

    @BeforeEach
    void setUp() {
        deserializer = new ObjectMapper().registerModule(new JavaTimeModule());
        generator = new EasyRandom();
    }

    @AfterEach
    void tearDown() {
    }


    @ParameterizedTest
    @ValueSource(ints = {2, 76, 33, 12})
    @DisplayName("Get all enrolled patients")
    public void testGetEnrolledPatients_returnAllEnrolledPatients(final int size) throws Exception {
        final RequestBuilder request = MockMvcRequestBuilders.get("/patients");

        final List<Patient> expectedPatients = new ArrayList<>();
        generator.objects(Patient.class, size).forEach(expectedPatients::add);

        when(patientRegistryService.getAllPatients()).thenReturn(expectedPatients);

        final MvcResult mvcResult = mockMvc.perform(request).andReturn();

        final ArrayList<Patient> patients = deserializer.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertEquals(size, patients.size());
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());

        for (int i = 0; i < size; i++) {
            Assertions.assertEquals(expectedPatients.get(i), patients.get(i));
        }
    }

    @Test
    @DisplayName("fetch patient by id")
    public void testGetPatient_givenPatientId_returnPatientInformation() throws Exception {
        final RequestBuilder request = MockMvcRequestBuilders.get("/patient/4");

        final Patient patient = generator.nextObject(Patient.class);

        when(patientRegistryService.getPatient(any(Integer.class))).thenReturn(Optional.of(patient));

        final MvcResult mvcResult = mockMvc.perform(request).andReturn();

        final Patient actualPatient = deserializer.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertEquals(patient, actualPatient);
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("fetch nothing when patient not found")
    public void testGetPatient_givenInvalidPatientId_returnNoContent() throws Exception {
        final RequestBuilder request = MockMvcRequestBuilders.get("/patient/-383");

        when(patientRegistryService.getPatient(any(Integer.class))).thenReturn(Optional.empty());

        final MvcResult mvcResult = mockMvc.perform(request).andReturn();

        Assertions.assertEquals("", mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());
    }

}