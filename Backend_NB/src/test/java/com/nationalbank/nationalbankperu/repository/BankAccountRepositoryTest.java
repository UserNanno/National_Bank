package com.nationalbank.nationalbankperu.repository;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Usa MySQL real en pruebas
class BankAccountRepositoryTest {

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Autowired
    private IUserRepository userRepository;  // Repositorio para guardar un usuario

    private User user;  // Usuario de prueba

    @BeforeEach
    void setUp() {

        String uniqueId = UUID.randomUUID().toString().substring(0, 8);  // Generar un ID único

        // Creamos y guardamos un usuario de prueba
        user = User.builder()
                .numIdentification(uniqueId) // ID único
                .firstName("Juan")
                .lastName("Pérez")
                .email("test" + uniqueId + "@example.com") // Email único
                .phone("987654" + uniqueId.substring(0, 3)) // Teléfono único
                .birthDate("1990-01-01")
                .password("encryptedpassword") // Simulando una contraseña encriptada
                .build();

        user = userRepository.save(user);  // Guardamos el usuario en la BD
    }



    @Test
    void testSaveAndFindById() {
        // Crear una cuenta bancaria asignada al usuario creado
        BankAccount account = new BankAccount();
        account.setAccountNumber("987654321");
        account.setBalance(new BigDecimal("5000.00"));
        account.setStatus("ACTIVE");
        account.setUser(user);  // Asignar usuario válido

        // Guardar en el repositorio
        BankAccount savedAccount = bankAccountRepository.save(account);

        // Buscar en la base de datos
        Optional<BankAccount> foundAccount = bankAccountRepository.findById(savedAccount.getId());

        // Verificar que se guardó correctamente
        assertTrue(foundAccount.isPresent());
        assertEquals("987654321", foundAccount.get().getAccountNumber());
        assertEquals(user.getId(), foundAccount.get().getUser().getId()); // Verificar que tiene un usuario asignado
    }
}
