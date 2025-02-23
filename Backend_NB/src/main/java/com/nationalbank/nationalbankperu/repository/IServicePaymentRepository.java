package com.nationalbank.nationalbankperu.repository;

import com.nationalbank.nationalbankperu.model.BankAccount;
import com.nationalbank.nationalbankperu.model.ServicePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IServicePaymentRepository extends JpaRepository<ServicePayment, Long> {

    /**
     * Encuentra todos los pagos de servicios asociados a una cuenta bancaria.
     *
     * @param bankAccount la cuenta bancaria desde donde se realizó el pago.
     * @return una lista de pagos de servicios realizados desde la cuenta.
     */
    List<ServicePayment> findByBankAccount(BankAccount bankAccount);

    /**
     * Encuentra todos los pagos de servicios realizados en un rango de fechas.
     *
     * @param startDate la fecha de inicio del rango.
     * @param endDate la fecha de fin del rango.
     * @return una lista de pagos de servicios dentro del rango de fechas.
     */
    List<ServicePayment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Busca un pago de servicio por su código único.
     *
     * @param serviceCode código del servicio pagado.
     * @return un `Optional<ServicePayment>` que puede contener el pago si existe.
     */
    ServicePayment findByServiceCode(String serviceCode);
}
