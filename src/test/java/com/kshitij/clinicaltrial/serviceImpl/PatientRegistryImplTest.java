package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.service.PatientRegistry;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.Random;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PatientRegistryImplTest {

    private PatientRegistry patientRegistry;


    @BeforeEach
    void setUp() {
        patientRegistry = new PatientRegistryImpl();
        patientRegistry.getAllPatients().clear();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @DisplayName("Adding patient to registry")
    public void testAddPatient_whenPatientProvided_thenAddPatientToRegistry() {
        final EasyRandom generator = new EasyRandom();
        final int size = new Random().nextInt(45,74);

        generator.objects(Patient.class, size)
                .forEach(p -> patientRegistry.addPatientToRegistry(p));

        assertEquals(size, patientRegistry.getAllPatients().size());
    }


    @ParameterizedTest()
    @MethodSource()
    @DisplayName("Get patients by id")
    public void testGetPatientById_whenGivenPatientId_returnPatient(final Patient patient) {
        patientRegistry.addPatientToRegistry(patient);

        final Optional<Patient> optionalPatient = patientRegistry.getPatient(patient.getId());

        final Patient actualPatient = optionalPatient.orElseGet(Patient::new);

        assertEquals(actualPatient, patient);
    }

    private static Stream<Arguments> testGetPatientById_whenGivenPatientId_returnPatient() {
        return Stream.of(
                arguments(new Patient("Red", 34, Gender.Male, "medical")),
                arguments(new Patient("Yellow", 64, Gender.Female, "medical")));
    }

}