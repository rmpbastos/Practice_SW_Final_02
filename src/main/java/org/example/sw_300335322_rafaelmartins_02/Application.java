package org.example.sw_300335322_rafaelmartins_02;

import org.example.sw_300335322_rafaelmartins_02.entities.Account;
import org.example.sw_300335322_rafaelmartins_02.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository) {

        return args -> {
            accountRepository.save(new Account(null, "115", "Jasper Diaz", 15000, 5, "Savings-Deluxe"));
            accountRepository.save(new Account(null, "112", "Zanip Mendez", 5000, 2, "Savings-Deluxe"));
            accountRepository.save(new Account(null, "113", "Geronima Esper", 6000, 5, "Savings-Regular"));
            accountRepository.findAll().forEach(p -> {
                System.out.println(p.getCustomerName());
            });
        };

    }


}
