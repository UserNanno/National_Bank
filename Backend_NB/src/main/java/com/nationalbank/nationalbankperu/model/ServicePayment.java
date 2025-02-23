package com.nationalbank.nationalbankperu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "service_payments") // Convención estándar para nombres de tablas
public class ServicePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String serviceType;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, unique = true, length = 50)
    private String serviceCode;

    @Column(nullable = false, precision = 15, scale = 2) // Definir precisión decimal
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    @PrePersist
    protected void onCreate() {
        this.paymentDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ServicePayment{" +
                "id=" + id +
                ", serviceType='" + serviceType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", bankAccountId=" + (bankAccount != null ? bankAccount.getId() : "null") +
                '}';
    }
}
