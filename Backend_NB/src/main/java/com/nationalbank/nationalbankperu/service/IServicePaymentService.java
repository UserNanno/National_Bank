package com.nationalbank.nationalbankperu.service;

import com.nationalbank.nationalbankperu.model.ServicePayment;

import java.util.List;
import java.util.Optional;

public interface IServicePaymentService {

    /**
     * Realiza el pago de un servicio a nombre de un usuario.
     *
     * @param userId         ID del usuario que realiza el pago.
     * @param servicePayment Datos del pago del servicio.
     * @return El pago del servicio registrado.
     */
    ServicePayment payService(Long userId, ServicePayment servicePayment);

    /**
     * Busca un pago de servicio por su ID.
     *
     * @param id el identificador del pago de servicio.
     * @return un `Optional<ServicePayment>` que puede contener el pago si existe.
     */
    Optional<ServicePayment> findById(Long id);

    /**
     * Obtiene todos los pagos de servicios registrados.
     *
     * @return una lista de pagos de servicios.
     */
    List<ServicePayment> findAll();

    /**
     * Elimina un pago de servicio por su ID.
     *
     * @param id el identificador del pago de servicio a eliminar.
     */
    void deleteById(Long id);
}
