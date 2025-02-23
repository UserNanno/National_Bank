package com.nationalbank.nationalbankperu.repository;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccount, Long> {

    /**
     * Encuentra una cuenta bancaria por su número de cuenta.
     *
     * @param accountNumber número de cuenta bancaria.
     * @return un `Optional<BankAccount>`, que puede contener la cuenta si existe.
     */
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    /**
     * Encuentra todas las cuentas bancarias asociadas a un usuario.
     *
     * @param user el usuario dueño de las cuentas bancarias.
     * @return una lista de `BankAccount` asociadas al usuario.
     */
    List<BankAccount> findByUser(User user);

    /**
     * Verifica si existe una cuenta bancaria con el número de cuenta especificado.
     *
     * @param accountNumber número de cuenta bancaria.
     * @return `true` si existe, `false` en caso contrario.
     */
    boolean existsByAccountNumber(String accountNumber);
}
