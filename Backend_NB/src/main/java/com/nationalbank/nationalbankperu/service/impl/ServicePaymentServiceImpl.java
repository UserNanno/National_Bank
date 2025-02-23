package com.nationalbank.nationalbankperu.service.impl;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.ServicePayment;
import com.nationalbank.nationalbankperu.model.User;
import com.nationalbank.nationalbankperu.repository.IBankAccountRepository;
import com.nationalbank.nationalbankperu.repository.IServicePaymentRepository;
import com.nationalbank.nationalbankperu.repository.IUserRepository;
import com.nationalbank.nationalbankperu.service.IServicePaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicePaymentServiceImpl implements IServicePaymentService {

    private final IServicePaymentRepository servicePaymentRepository;
    private final IUserRepository userRepository;
    private final IBankAccountRepository bankAccountRepository;

    public ServicePaymentServiceImpl(
            IServicePaymentRepository servicePaymentRepository,
            IUserRepository userRepository,
            IBankAccountRepository bankAccountRepository) {
        this.servicePaymentRepository = servicePaymentRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public ServicePayment payService(Long userId, ServicePayment servicePayment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        BankAccount bankAccount = bankAccountRepository
                .findByAccountNumber(servicePayment.getBankAccount().getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Cuenta bancaria no encontrada"));

        if (bankAccount.getBalance().compareTo(servicePayment.getAmount()) < 0) {
            throw new RuntimeException("Fondos insuficientes");
        }

        // Actualizar saldo de la cuenta
        bankAccount.setBalance(bankAccount.getBalance().subtract(servicePayment.getAmount()));
        bankAccountRepository.save(bankAccount);

        // Guardar el pago de servicio
        servicePayment.setUser(user);
        servicePayment.setPaymentDate(LocalDateTime.now());
        servicePayment.setBankAccount(bankAccount);
        return servicePaymentRepository.save(servicePayment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServicePayment> findById(Long id) {
        return servicePaymentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicePayment> findAll() {
        return servicePaymentRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        servicePaymentRepository.deleteById(id);
    }
}
