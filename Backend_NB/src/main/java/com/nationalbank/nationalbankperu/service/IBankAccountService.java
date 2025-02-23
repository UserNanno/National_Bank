package com.nationalbank.nationalbankperu.service;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.User;

import java.util.List;
import java.util.Optional;

public interface IBankAccountService {

    /**
     * Obtiene todas las cuentas bancarias registradas.
     *
     * @return una lista de cuentas bancarias.
     */
    List<BankAccount> findAll();

    /**
     * Busca una cuenta bancaria por su ID.
     *
     * @param id el identificador de la cuenta bancaria.
     * @return un `Optional<BankAccount>` que puede contener la cuenta si existe.
     */
    Optional<BankAccount> findById(Long id);

    /**
     * Guarda o actualiza una cuenta bancaria en la base de datos.
     *
     * @param bankAccount la cuenta bancaria a guardar o actualizar.
     * @return la cuenta guardada.
     */
    BankAccount save(BankAccount bankAccount);

    /**
     * Elimina una cuenta bancaria por su ID.
     *
     * @param id el identificador de la cuenta bancaria a eliminar.
     */
    void deleteById(Long id);

    /**
     * Busca todas las cuentas bancarias de un usuario específico.
     *
     * @param user el usuario dueño de las cuentas bancarias.
     * @return una lista de cuentas bancarias asociadas al usuario.
     */
    List<BankAccount> findByUser(User user);

    /**
     * Busca una cuenta bancaria por su número de cuenta.
     *
     * @param accountNumber el número de cuenta bancaria.
     * @return un `Optional<BankAccount>` con la cuenta si existe.
     */
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    /**
     * Verifica si una cuenta bancaria existe por su número de cuenta.
     *
     * @param accountNumber número de cuenta bancaria.
     * @return `true` si existe, `false` en caso contrario.
     */
    boolean existsByAccountNumber(String accountNumber);
}
