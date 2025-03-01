package com.nationalbank.nationalbankperu.controller;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.User;
import com.nationalbank.nationalbankperu.service.IBankAccountService;
import com.nationalbank.nationalbankperu.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank-accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Cuentas Bancarias", description = "API para gestionar cuentas bancarias")
public class BankAccountController {

    private final IBankAccountService bankAccountService;
    private final IUserService userService;

    public BankAccountController(IBankAccountService bankAccountService, IUserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }

    @Operation(summary = "Obtener todas las cuentas bancarias", description = "Devuelve una lista de todas las cuentas bancarias registradas.")
    @GetMapping
    public ResponseEntity<List<BankAccount>> findAll() {
        return ResponseEntity.ok(bankAccountService.findAll());
    }

    @Operation(summary = "Buscar cuenta por ID", description = "Devuelve los detalles de una cuenta bancaria por su ID.")
    @ApiResponse(responseCode = "200", description = "Cuenta bancaria encontrada")
    @ApiResponse(responseCode = "404", description = "Cuenta bancaria no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> findById(@PathVariable Long id) {
        return bankAccountService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva cuenta bancaria", description = "Crea una cuenta bancaria para un usuario específico.")
    @ApiResponse(responseCode = "200", description = "Cuenta bancaria creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error: Usuario no encontrado")
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

    @Operation(summary = "Actualizar cuenta bancaria", description = "Modifica el estado, saldo o número de cuenta bancaria existente.")
    @ApiResponse(responseCode = "200", description = "Cuenta bancaria actualizada exitosamente")
    @ApiResponse(responseCode = "400", description = "Error: Cuenta bancaria no encontrada")
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

    @Operation(summary = "Eliminar cuenta bancaria", description = "Elimina una cuenta bancaria por su ID.")
    @ApiResponse(responseCode = "200", description = "Cuenta bancaria eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Cuenta bancaria no encontrada")
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
