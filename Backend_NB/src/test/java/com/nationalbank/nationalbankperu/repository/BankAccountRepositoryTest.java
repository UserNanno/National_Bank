package com.nationalbank.nationalbankperu.repository;

import com.nationalbank.nationalbankperu.model.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Test
    void testSaveAndFindById() {
        BankAccount account = new BankAccount();
        account.setAccountNumber("987654321");
        account.setBalance(new BigDecimal("5000.00"));
        account.setStatus("ACTIVE");

        BankAccount savedAccount = bankAccountRepository.save(account);
        Optional<BankAccount> foundAccount = bankAccountRepository.findById(savedAccount.getId());

        assertTrue(foundAccount.isPresent());
        assertEquals("987654321", foundAccount.get().getAccountNumber());
    }
}
