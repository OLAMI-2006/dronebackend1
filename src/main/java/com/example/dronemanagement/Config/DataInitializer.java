package com.example.dronemanagement.Config;

import com.example.dronemanagement.model.Medication;
import com.example.dronemanagement.Repository.MedicationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(MedicationRepository medicationRepository) {
        return args -> {
            if (medicationRepository.count() == 0) {
                Medication med1 = new Medication();
                med1.setName("O- Blood Unit");
                med1.setCode("BLD_O_NEG_01");
                med1.setType("BLOOD");
                med1.setWeight(0.5);
                med1.setQuantity(45.0);

                Medication med2 = new Medication();
                med2.setName("COVID Vaccine Vials");
                med2.setCode("VAC_COVID_02");
                med2.setType("VACCINES");
                med2.setWeight(0.2);
                med2.setQuantity(120.00);

                Medication med3 = new Medication();
                med3.setName("Rabies Anti-Venom");
                med3.setCode("EMG_RABIES_03");
                med3.setType("EMERGENCY");
                med3.setWeight(0.3);
                med3.setQuantity(15.00);

                Medication med4 = new Medication();
                med4.setName("A+ Plasma Pack");
                med4.setCode("BLD_A_POS_04");
                med4.setType("BLOOD");
                med4.setWeight(0.5);
                med4.setQuantity(80.0);

                Medication med5 = new Medication();
                med5.setName("Trauma First Aid Kit");
                med5.setCode("EMG_TRAUMA_05");
                med5.setType("EMERGENCY");
                med5.setWeight(1.2);
                med5.setQuantity(60.0);

                medicationRepository.save(med1);
                medicationRepository.save(med2);
                medicationRepository.save(med3);
                medicationRepository.save(med4);
                medicationRepository.save(med5);

                System.out.println("Default medical inventory seeded successfully into the database!");
            }
        };
    }
}