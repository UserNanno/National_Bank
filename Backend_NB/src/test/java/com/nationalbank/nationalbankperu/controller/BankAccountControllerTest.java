package com.nationalbank.nationalbankperu.controller;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.service.IBankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BankAccountControllerTest {

    @Mock
    private IBankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    private MockMvc mockMvc;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountNumber("123456789");
        bankAccount.setBalance(new BigDecimal("1000.00"));
        bankAccount.setStatus("ACTIVE");
    }

    @Test
    void testGetBankAccountById() throws Exception {
        when(bankAccountService.findById(1L)).thenReturn(Optional.of(bankAccount));

        mockMvc.perform(get("/api/bank-accounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("123456789"));
    }
}
