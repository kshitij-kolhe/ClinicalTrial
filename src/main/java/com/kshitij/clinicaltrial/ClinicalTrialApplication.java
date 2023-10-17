package com.kshitij.clinicaltrial;

import com.kshitij.clinicaltrial.service.PatientRegistryService;
import com.kshitij.clinicaltrial.serviceImpl.PatientRegistryServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.kshitij.clinicaltrial.serviceImpl", "com.kshitij.clinicaltrial.controller"})
public class ClinicalTrialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicalTrialApplication.class, args);
        final PatientRegistryService patientRegistryService = new PatientRegistryServiceImpl();
    }

}
