package com.nationalbank.nationalbankperu.controller;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.User;
import com.nationalbank.nationalbankperu.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> save(@RequestBody User user) {
        if (userService.existsByNumIdentification(user.getNumIdentification())) {
            return ResponseEntity.badRequest().body("Error: NumIdentification ya existe en la BD!");
        }
        userService.save(user);
        return ResponseEntity.ok("Usuario guardado exitosamente!");
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario no encontrado!");
        }
        userService.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> userToLogin = userService.findByNumIdentification(user.getNumIdentification());

        if (userToLogin.isPresent() && userToLogin.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(userToLogin.get());
        }
        return ResponseEntity.badRequest().body("Credenciales inválidas.");
    }

    @GetMapping("/{userId}/bankAccounts")
    public ResponseEntity<List<BankAccount>> findBankAccountsById(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        return user.map(u -> ResponseEntity.ok(u.getBankAccounts()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
