package com.kshitij.clinicaltrial.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientModifyService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = ModifierController.class)
class ModifierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientModifyService patientModifyService;

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


    @Test
    @DisplayName("Delete patient by id")
    public void testRemovePatientFromTrial_givenPatientId_thenRemovePatientFromRegistry() throws Exception {
        final RequestBuilder request = MockMvcRequestBuilders.delete("/patient/45");

        when(patientModifyService.deletePatientFromRegistry(anyInt())).thenReturn(true);

        final MvcResult mvcResult = mockMvc.perform(request).andReturn();

        Assertions.assertEquals(204, mvcResult.getResponse().getStatus());
    }


    @Test
    @DisplayName("Delete nothing for invalid patient id")
    public void testRemovePatientFromTrial_givenInvalidPatientId_thenDoNothing() throws Exception {
        final RequestBuilder request = MockMvcRequestBuilders.delete("/patient/-45");

        when(patientModifyService.deletePatientFromRegistry(anyInt())).thenReturn(false);

        final MvcResult mvcResult = mockMvc.perform(request).andReturn();

        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("update patient info")
    public void testUpdatePatientInfo_givenPatientId_returnUpdatedPatientInfo(final Patient patient) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (patient.getName() != null) params.add("name", patient.getName());
        if (patient.getAge() != null) params.add("age", patient.getAge().toString());
        if (patient.getGender() != null) params.add("gender", patient.getGender().toString());
        if (patient.getMedicalCondition() != null) params.add("medicalCondition", patient.getMedicalCondition());

        final RequestBuilder request = MockMvcRequestBuilders.put("/patient/45")
                .params(params);

        when(patientModifyService.updatePatientInfo(anyInt(), nullable(String.class), nullable(Integer.class), nullable(Gender.class), nullable(String.class)))
                .thenReturn(Optional.of(patient));

        final MvcResult mvcResult = mockMvc.perform(request).andReturn();
        final Patient actualPatient = deserializer.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        verify(patientModifyService, times(1)).updatePatientInfo(anyInt(), nullable(String.class), nullable(Integer.class), nullable(Gender.class), nullable(String.class));

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(patient, actualPatient);
    }

    public static Stream<Arguments> testUpdatePatientInfo_givenPatientId_returnUpdatedPatientInfo() {
        return Stream.of(
                arguments(new Patient("Red", 98, null, null)),
                arguments(new Patient(null, null, Gender.Female, "Diabetes")),
                arguments(new Patient(null, 95, null, "Tumor")),
                arguments(new Patient("RED", null, Gender.Male, null))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("do nothing for invalid patient id")
    public void testUpdatePatientInfo_givenInvalidPatientId_returnNothing(final Patient patient) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (patient.getName() != null) params.add("name", patient.getName());
        if (patient.getAge() != null) params.add("age", patient.getAge().toString());
        if (patient.getGender() != null) params.add("gender", patient.getGender().toString());
        if (patient.getMedicalCondition() != null) params.add("medicalCondition", patient.getMedicalCondition());

        final RequestBuilder request = MockMvcRequestBuilders.put("/patient/-45")
                .params(params);

        when(patientModifyService.updatePatientInfo(anyInt(), nullable(String.class), nullable(Integer.class), nullable(Gender.class), nullable(String.class)))
                .thenReturn(Optional.empty());

        final MvcResult mvcResult = mockMvc.perform(request).andReturn();

        verify(patientModifyService, times(1)).updatePatientInfo(anyInt(), nullable(String.class), nullable(Integer.class), nullable(Gender.class), nullable(String.class));
        Assertions.assertEquals(204, mvcResult.getResponse().getStatus());
        Assertions.assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    public static Stream<Arguments> testUpdatePatientInfo_givenInvalidPatientId_returnNothing() {
        return Stream.of(
                arguments(new Patient("red", 98, null, null)),
                arguments(new Patient(null, null, null, null)),
                arguments(new Patient(null, 98, null, "Tumor")),
                arguments(new Patient("red", null, Gender.Male, null))
        );
    }
}