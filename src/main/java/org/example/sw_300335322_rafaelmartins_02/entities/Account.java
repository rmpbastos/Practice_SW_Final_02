package org.example.sw_300335322_rafaelmartins_02.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // This id is not used, but an @Entity needs a primary key

    private String customerNumber;
    private String customerName;
    private double customerDeposit;
    private int numberOfYears;
    private String savingsType;
    
}
