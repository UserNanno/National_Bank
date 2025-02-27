package com.nationalbank.nationalbankperu.service.impl;

import com.nationalbank.nationalbankperu.exception.BankAccountNotFoundException;
import com.nationalbank.nationalbankperu.exception.InactiveBankAccountException;
import com.nationalbank.nationalbankperu.exception.InsufficientFundsException;
import com.nationalbank.nationalbankperu.exception.InvalidTransactionAmountException;
import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.Transaction;
import com.nationalbank.nationalbankperu.repository.IBankAccountRepository;
import com.nationalbank.nationalbankperu.repository.ITransactionRepository;
import com.nationalbank.nationalbankperu.repository.IUserRepository;
import com.nationalbank.nationalbankperu.service.ITransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final IBankAccountRepository bankAccountRepository;


    public TransactionServiceImpl(
            ITransactionRepository transactionRepository,
            IBankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Transaction performTransaction(Transaction transaction) {

        String fromAccountNumber = transaction.getFromAccount().getAccountNumber();
        String toAccountNumber = transaction.getToAccount().getAccountNumber();

        BankAccount fromAccount = bankAccountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Cuenta de origen no encontrada: " + fromAccountNumber));

        BankAccount toAccount = bankAccountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Cuenta de destino no encontrada: " + toAccountNumber));

        validateAccountAndTransaction(transaction, fromAccount, toAccount);

        fromAccount.setBalance(fromAccount.getBalance().subtract(transaction.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transaction.getAmount()));

        // Persistiendo los cambios en las cuentas bancarias
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        // Persistiendo la transacción
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    private void validateAccountAndTransaction(Transaction transaction, BankAccount fromAccount, BankAccount toAccount) {
        if (!"ACTIVE".equals(fromAccount.getStatus()))  {
            throw new InactiveBankAccountException("La cuenta de origen no está activa: " + fromAccount.getAccountNumber());
        }

        if (!"ACTIVE".equals(toAccount.getStatus())) {
            throw new InactiveBankAccountException("La cuenta de destino no está activa: " + toAccount.getAccountNumber());
        }

        if (fromAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new InsufficientFundsException("Fondos insuficientes en la cuenta de origen: " + fromAccount.getAccountNumber());
        }

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionAmountException("El monto a transferir debe ser mayor a 0.");
        }
    }
}
