package com.nationalbank.nationalbankperu.service;

import com.nationalbank.nationalbankperu.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {

    /**
     * Obtiene todas las transacciones registradas.
     *
     * @return una lista de transacciones.
     */
    List<Transaction> findAll();

    /**
     * Busca una transacción por su ID.
     *
     * @param id el identificador de la transacción.
     * @return un `Optional<Transaction>` que puede contener la transacción si existe.
     */
    Optional<Transaction> findById(Long id);

    /**
     * Guarda o actualiza una transacción en la base de datos.
     *
     * @param transaction la transacción a guardar o actualizar.
     * @return la transacción guardada.
     */
    Transaction save(Transaction transaction);

    /**
     * Elimina una transacción por su ID.
     *
     * @param id el identificador de la transacción a eliminar.
     */
    void deleteById(Long id);

    /**
     * Realiza una transacción entre cuentas bancarias.
     *
     * @param transaction la transacción a ejecutar.
     * @return la transacción completada.
     */
    Transaction performTransaction(Transaction transaction);

    /**
     * Encuentra todas las transacciones realizadas en un rango de fechas.
     *
     * @param startDate la fecha de inicio del rango.
     * @param endDate la fecha de fin del rango.
     * @return una lista de transacciones dentro del rango de fechas.
     */
    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
