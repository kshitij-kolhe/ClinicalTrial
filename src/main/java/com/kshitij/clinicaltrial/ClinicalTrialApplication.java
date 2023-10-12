package com.kshitij.clinicaltrial;

import com.kshitij.clinicaltrial.service.PatientRegistry;
import com.kshitij.clinicaltrial.serviceImpl.PatientRegistryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.kshitij.clinicaltrial.serviceImpl", "com.kshitij.clinicaltrial.controller"})
public class ClinicalTrialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicalTrialApplication.class, args);
        final PatientRegistry patientRegistry = new PatientRegistryImpl();
    }

}
