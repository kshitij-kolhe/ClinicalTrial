package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.model.PatientInfo;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnrollPatientsServiceImplTest {

    @Mock
    private PatientRegistryService patientRegistryService;

    @InjectMocks
    private EnrollPatientsServiceImpl enrollPatients;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("enroll patients for clinical trial")
    void testEnrollForClinicalTrial_whenPatientInfoProvided_thenEnrollPatient(final PatientInfo patientInfo) {

        doNothing().when(patientRegistryService).addPatientToRegistry(any(Patient.class));

        final boolean created = enrollPatients.enrollForClinicalTrial(patientInfo);

        verify(patientRegistryService, times(1)).addPatientToRegistry(any(Patient.class));
        Assertions.assertTrue(created);
    }

    public static Stream<Arguments> testEnrollForClinicalTrial_whenPatientInfoProvided_thenEnrollPatient() {
        return Stream.of(
                arguments(new PatientInfo("Red", 34, Gender.Male, "medical")),
                arguments(new PatientInfo("Yellow", 64, Gender.Female, "medical")));
    }


    @Test
    @DisplayName("enroll patients only once for clinical trial")
    void testEnrollForClinicalTrial_whenDuplicatePatientInfoProvided_thenEnrollPatientOnlyOnce() {
        final EasyRandom generator = new EasyRandom();
        final PatientInfo patientInfo = generator.nextObject(PatientInfo.class);

        final List<Patient> patients = new ArrayList<>();

        doNothing().when(patientRegistryService).addPatientToRegistry(any(Patient.class));
        when(patientRegistryService.getAllPatients()).thenReturn(patients);

        enrollPatients.enrollForClinicalTrial(patientInfo);

        patients.add(new Patient(patientInfo.getName(), patientInfo.getAge(), patientInfo.getGender(), patientInfo.getMedicalCondition()));

        final boolean created = enrollPatients.enrollForClinicalTrial(patientInfo);

        verify(patientRegistryService, times(1)).addPatientToRegistry(any(Patient.class));

        Assertions.assertFalse(created);
    }


}