package org.example.sw_300335322_rafaelmartins_02.repositories;

import org.example.sw_300335322_rafaelmartins_02.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // Used to check existing customerNumber
    boolean existsByCustomerNumber(String customerNumber);

//    List<Account> findByCustomerNumber(String customerNumber);

}
