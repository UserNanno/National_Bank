package com.nationalbank.nationalbankperu.repository;

import com.nationalbank.nationalbankperu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    // Verifica si un usuario existe por su número de identificación
    boolean existsByNumIdentification(String numIdentification);

    // Encuentra un usuario por su número de identificación
    Optional<User> findByNumIdentification(String numIdentification);

    // Encuentra un usuario por su correo electrónico
    Optional<User> findByEmail(String email);

    // Elimina un usuario por su número de identificación
    void deleteByNumIdentification(String numIdentification);
}
