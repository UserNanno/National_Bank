package com.nationalbank.nationalbankperu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String numIdentification;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 10)
    private String birthDate;

    @Column(nullable = false, length = 255) // Se almacena la contraseña encriptada
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccount> bankAccounts;

    @Embedded
    private Audit audit = new Audit();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", numIdentification='" + numIdentification + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", password='********'" +  // Seguridad: No mostrar la contraseña
                ", created_at=" + audit.getCreatedAt() +
                ", updated_at=" + audit.getUpdatedAt() +
                ", bankAccounts=" + (bankAccounts != null ? bankAccounts.size() : "0") +
                '}';
    }
}
