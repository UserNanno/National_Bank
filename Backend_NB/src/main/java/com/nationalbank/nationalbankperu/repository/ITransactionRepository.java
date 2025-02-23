package com.nationalbank.nationalbankperu.repository;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Encuentra todas las transacciones realizadas por una cuenta bancaria.
     *
     * @param fromAccount la cuenta de origen.
     * @return una lista de transacciones realizadas desde la cuenta especificada.
     */
    List<Transaction> findByFromAccount(BankAccount fromAccount);

    /**
     * Encuentra todas las transacciones recibidas por una cuenta bancaria.
     *
     * @param toAccount la cuenta de destino.
     * @return una lista de transacciones recibidas por la cuenta especificada.
     */
    List<Transaction> findByToAccount(BankAccount toAccount);

    /**
     * Encuentra todas las transacciones realizadas en un rango de fechas.
     *
     * @param startDate la fecha de inicio del rango.
     * @param endDate la fecha de fin del rango.
     * @return una lista de transacciones dentro del rango de fechas.
     */
    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
