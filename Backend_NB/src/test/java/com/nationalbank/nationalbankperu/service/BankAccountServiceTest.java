package com.nationalbank.nationalbankperu.service;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.repository.IBankAccountRepository;
import com.nationalbank.nationalbankperu.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private IBankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountNumber("123456789");
        bankAccount.setBalance(new BigDecimal("1000.00"));
        bankAccount.setStatus("ACTIVE");
    }

    @Test
    void testFindById() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));

        Optional<BankAccount> found = bankAccountService.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(bankAccount.getAccountNumber(), found.get().getAccountNumber());
    }

    @Test
    void testSaveAccount() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccount savedAccount = bankAccountService.save(bankAccount);

        assertNotNull(savedAccount);
        assertEquals("123456789", savedAccount.getAccountNumber());
    }
}
