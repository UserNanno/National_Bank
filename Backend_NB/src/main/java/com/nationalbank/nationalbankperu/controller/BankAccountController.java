package com.nationalbank.nationalbankperu.controller;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.User;
import com.nationalbank.nationalbankperu.service.IBankAccountService;
import com.nationalbank.nationalbankperu.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank-accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class BankAccountController {

    private final IBankAccountService bankAccountService;
    private final IUserService userService;

    public BankAccountController(IBankAccountService bankAccountService, IUserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> findAll() {
        List<BankAccount> bankAccounts = bankAccountService.findAll();
        return ResponseEntity.ok(bankAccounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> findById(@PathVariable Long id) {
        Optional<BankAccount> bankAccount = bankAccountService.findById(id);
        return bankAccount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createBankAccount(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado.");
        }

        BankAccount bankAccount = BankAccount.builder()
                .status("ACTIVE")
                .accountNumber(generateAccountNumber())
                .balance(new BigDecimal(80000))
                .user(user.get())
                .build();

        bankAccountService.save(bankAccount);
        return ResponseEntity.ok("Cuenta bancaria creada exitosamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBankAccount(@PathVariable Long id, @RequestBody BankAccount bankAccount) {
        Optional<BankAccount> existingBankAccount = bankAccountService.findById(id);
        if (existingBankAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Cuenta bancaria no encontrada.");
        }

        BankAccount updatedBankAccount = existingBankAccount.get();
        if (bankAccount.getStatus() != null) {
            updatedBankAccount.setStatus(bankAccount.getStatus());
        }
        if (bankAccount.getBalance() != null) {
            updatedBankAccount.setBalance(bankAccount.getBalance());
        }
        if (bankAccount.getAccountNumber() != null) {
            updatedBankAccount.setAccountNumber(bankAccount.getAccountNumber());
        }

        bankAccountService.save(updatedBankAccount);
        return ResponseEntity.ok("Cuenta bancaria actualizada exitosamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (bankAccountService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bankAccountService.deleteById(id);
        return ResponseEntity.ok("Cuenta bancaria eliminada correctamente.");
    }

    private String generateAccountNumber() {
        return "PE" + UUID.randomUUID().toString().replace("-", "").substring(0, 9).toUpperCase();
    }
}
