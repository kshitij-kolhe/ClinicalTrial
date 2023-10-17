package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientModifyServiceImplTest {


    @Mock
    private PatientRegistryService patientRegistryService;

    @InjectMocks
    private PatientModifyServiceImpl patientModifyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 56, 323})
    @DisplayName("delete patient from patient registry")
    public void testDeletePatientRecord_givePatientId_returnTrue(final int patientId) {

        when(patientRegistryService.deletePatient(anyInt())).thenReturn(true);

        final boolean deleted = patientModifyService.deletePatientFromRegistry(patientId);

        Assertions.assertTrue(deleted);
        verify(patientRegistryService, times(1)).deletePatient(patientId);
    }

    @ParameterizedTest
    @ValueSource(ints = {-4, -56, 323})
    @DisplayName("delete nothing for invalid id from patient registry")
    public void testDeletePatientRecord_giveInvalidPatientId_returnFalse(final int patientId) {

        when(patientRegistryService.deletePatient(anyInt())).thenReturn(false);

        final boolean deleted = patientModifyService.deletePatientFromRegistry(patientId);

        Assertions.assertFalse(deleted);
        verify(patientRegistryService, times(1)).deletePatient(patientId);
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("update patient info")
    public void testUpdatePatient_givenUpdateInfo_returnUpdatedPatient(final int patientId, final String name, final Integer age, final Gender gender, final String medicalCondition) {

        when(patientRegistryService.getPatient(anyInt())).thenReturn(Optional.of(new Patient("rED", 90, Gender.Male, "Migraine")));

        final Optional<Patient> patient = patientModifyService.updatePatientInfo(patientId, name, age, gender, medicalCondition);

        verify(patientRegistryService, times(1)).getPatient(patientId);

        Assertions.assertTrue(patient.isPresent());

        if (name != null) Assertions.assertEquals(name, patient.get().getName());

        if (age != null) Assertions.assertEquals(age, patient.get().getAge());

        if (gender != null) Assertions.assertEquals(gender, patient.get().getGender());

        if (medicalCondition != null) Assertions.assertEquals(medicalCondition, patient.get().getMedicalCondition());
    }

    private static Stream<Arguments> testUpdatePatient_givenUpdateInfo_returnUpdatedPatient() {
        return Stream.of(
          arguments(23, "red", 33, null, null),
          arguments(23, "Red", null, null, null),
          arguments(78, "RED", 65, Gender.Female, null),
          arguments(23, null, 22, null, "Diabetes")
        );
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("update nothing for invalid patient id")
    public void testUpdatePatient_givenInvalidUpdateInfo_returnNothing(final int patientId, final String name, final Integer age, final Gender gender, final String medicalCondition) {

        when(patientRegistryService.getPatient(anyInt())).thenReturn(Optional.empty());

        final Optional<Patient> patient = patientModifyService.updatePatientInfo(patientId, name, age, gender, medicalCondition);

        verify(patientRegistryService, times(1)).getPatient(patientId);

        Assertions.assertTrue(patient.isEmpty());
    }

    private static Stream<Arguments> testUpdatePatient_givenInvalidUpdateInfo_returnNothing() {
        return Stream.of(
                arguments(-23, "red", 33, null, null),
                arguments(65, "Red", null, null, null),
                arguments(84487, "RED", 65, Gender.Female, null),
                arguments(-23, null, 22, null, "Diabetes")
        );
    }
}