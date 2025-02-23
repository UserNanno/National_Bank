package com.nationalbank.nationalbankperu.controller;

import com.nationalbank.nationalbankperu.model.Transaction;
import com.nationalbank.nationalbankperu.service.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TransactionController {

    private final ITransactionService transactionService;

    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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
