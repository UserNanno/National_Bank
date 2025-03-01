package com.nationalbank.nationalbankperu.controller;

import com.nationalbank.nationalbankperu.model.Transaction;
import com.nationalbank.nationalbankperu.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Transacciones", description = "API para gestionar transacciones bancarias")
public class TransactionController {

    private final ITransactionService transactionService;

    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Obtener todas las transacciones", description = "Devuelve una lista de todas las transacciones registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Buscar una transacción por ID", description = "Devuelve los detalles de una transacción por su ID.")
    @ApiResponse(responseCode = "200", description = "Transacción encontrada")
    @ApiResponse(responseCode = "404", description = "Transacción no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Realizar una transferencia", description = "Ejecuta una transacción entre cuentas bancarias.")
    @ApiResponse(responseCode = "200", description = "Transacción realizada con éxito")
    @ApiResponse(responseCode = "400", description = "Error en la transacción")
    @PostMapping("/transfer")
    public ResponseEntity<String> performTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction completedTransaction = transactionService.performTransaction(transaction);
            return ResponseEntity.ok("Transacción realizada con éxito. Monto: " + completedTransaction.getAmount());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en la transacción: " + e.getMessage());
        }
    }
}
