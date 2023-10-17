package com.kshitij.clinicaltrial.serviceImpl;

import com.kshitij.clinicaltrial.model.Gender;
import com.kshitij.clinicaltrial.model.Patient;
import com.kshitij.clinicaltrial.model.PatientInfo;
import com.kshitij.clinicaltrial.service.PatientRegistryService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.Random;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PatientRegistryServiceImplTest {

    private PatientRegistryService patientRegistryService;

    private EasyRandom generator;
    private Random random;

    @BeforeEach
    void setUp() {
        patientRegistryService = new PatientRegistryServiceImpl();
        patientRegistryService.getAllPatients().clear();
        generator = new EasyRandom();
        random = new Random();
    }

    @AfterEach
    void tearDown() {
    }


    @ParameterizedTest
    @ValueSource(ints = {24, 64, 5})
    @DisplayName("Adding patient to registry")
    public void testAddPatient_whenPatientProvided_thenAddPatientToRegistry(final int size) {

        generator.objects(Patient.class, size)
                .forEach(p -> patientRegistryService.addPatientToRegistry(p));

        assertEquals(size, patientRegistryService.getAllPatients().size());
    }


    @ParameterizedTest()
    @MethodSource()
    @DisplayName("Get patients by id")
    public void testGetPatientById_whenGivenPatientId_returnPatient(final Patient patient) {
        patientRegistryService.addPatientToRegistry(patient);

        final Optional<Patient> optionalPatient = patientRegistryService.getPatient(patient.getId());

        final Patient actualPatient = optionalPatient.orElseGet(Patient::new);

        assertEquals(actualPatient, patient);
    }

    private static Stream<Arguments> testGetPatientById_whenGivenPatientId_returnPatient() {
        return Stream.of(
                arguments(new Patient("Red", 34, Gender.Male, "medical")),
                arguments(new Patient("Yellow", 64, Gender.Female, "medical")));
    }


    @ParameterizedTest
    @ValueSource(ints = {54, 30})
    @DisplayName("delete patient from registry")
    public void testDeletePatient_givenValidPatientId_returnTrue(final int size) {

        generator.objects(PatientInfo.class, size)
                .forEach(pi -> {
                    final Patient patient = new Patient(pi.getName(), pi.getAge(), pi.getGender(), pi.getMedicalCondition());
                    patientRegistryService.addPatientToRegistry(patient);
                });

        int patientId = random.nextInt(Patient.getEnrolledPatients() - size + 1, Patient.getEnrolledPatients());
        Assertions.assertTrue(patientRegistryService.deletePatient(patientId));

        patientId = random.nextInt(Patient.getEnrolledPatients() - size + 1, Patient.getEnrolledPatients());
        Assertions.assertTrue(patientRegistryService.deletePatient(patientId));
    }

    @ParameterizedTest
    @ValueSource(ints = {34, 60, 190})
    @DisplayName("delete nothing for invalid patient id from registry")
    public void testDeletePatient_givenInvalidPatientId_returnFalse(final int size) {

        generator.objects(PatientInfo.class, size)
                .forEach(pi -> {
                    final Patient patient = new Patient(pi.getName(), pi.getAge(), pi.getGender(), pi.getMedicalCondition());
                    patientRegistryService.addPatientToRegistry(patient);
                });

        int patientId = random.nextInt(-78, -4);
        Assertions.assertFalse(patientRegistryService.deletePatient(patientId));

        patientId = random.nextInt(Patient.getEnrolledPatients() + size + random.nextInt(45, 86), Patient.getEnrolledPatients() + size + random.nextInt(145, 186));
        Assertions.assertFalse(patientRegistryService.deletePatient(patientId));
    }

    @ParameterizedTest
    @ValueSource(ints = {98, 55, 111})
    @DisplayName("deleting same patient twice from registry")
    public void testDeletePatient_givenDuplicatePatientId_returnFalse(final int size) {

        generator.objects(PatientInfo.class, size)
                .forEach(pi -> {
                    final Patient patient = new Patient(pi.getName(), pi.getAge(), pi.getGender(), pi.getMedicalCondition());
                    patientRegistryService.addPatientToRegistry(patient);
                });

        int patientId = random.nextInt(Patient.getEnrolledPatients() - size + 1, Patient.getEnrolledPatients());
        Assertions.assertTrue(patientRegistryService.deletePatient(patientId));

        Assertions.assertFalse(patientRegistryService.deletePatient(patientId));
    }

}