package com.nationalbank.nationalbankperu.controller;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.User;
import com.nationalbank.nationalbankperu.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Usuarios", description = "API para la gestión de usuarios y autenticación")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar un usuario por ID", description = "Devuelve los datos de un usuario por su ID.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar un usuario", description = "Guarda un nuevo usuario en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito")
    @ApiResponse(responseCode = "400", description = "Error: NumIdentification ya existe en la BD")
    @PostMapping("/register")
    public ResponseEntity<String> save(@RequestBody User user) {
        if (userService.existsByNumIdentification(user.getNumIdentification())) {
            return ResponseEntity.badRequest().body("Error: NumIdentification ya existe en la BD!");
        }
        userService.save(user);
        return ResponseEntity.ok("Usuario guardado exitosamente!");
    }

    @Operation(summary = "Actualizar un usuario", description = "Modifica los datos de un usuario existente.")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito")
    @ApiResponse(responseCode = "400", description = "Error: Usuario no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userToUpdate = userService.findById(id);
        if (userToUpdate.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado!");
        }

        User updatedUser = userToUpdate.get();
        updatedUser.setNumIdentification(user.getNumIdentification());
        updatedUser.setPassword(user.getPassword());
        userService.save(updatedUser);

        return ResponseEntity.ok("Usuario actualizado exitosamente!");
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario de la base de datos por su ID.")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado con éxito")
    @ApiResponse(responseCode = "400", description = "Error: Usuario no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado!");
        }
        userService.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

    @Operation(summary = "Iniciar sesión", description = "Verifica las credenciales del usuario y devuelve su información si son correctas.")
    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso")
    @ApiResponse(responseCode = "400", description = "Credenciales inválidas")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> userToLogin = userService.findByNumIdentification(user.getNumIdentification());

        if (userToLogin.isPresent() && userToLogin.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(userToLogin.get());
        }
        return ResponseEntity.badRequest().body("Credenciales inválidas.");
    }

    @Operation(summary = "Obtener cuentas bancarias de un usuario", description = "Devuelve una lista de cuentas bancarias asociadas a un usuario por su ID.")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas bancarias obtenida con éxito")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{userId}/bankAccounts")
    public ResponseEntity<List<BankAccount>> findBankAccountsById(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        return user.map(u -> ResponseEntity.ok(u.getBankAccounts()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
