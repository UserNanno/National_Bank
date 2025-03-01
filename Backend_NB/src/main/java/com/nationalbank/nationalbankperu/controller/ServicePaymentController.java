package com.nationalbank.nationalbankperu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nationalbank.nationalbankperu.model.ServicePayment;
import com.nationalbank.nationalbankperu.service.IServicePaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/service-payments")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Pagos de Servicios", description = "API para gestionar los pagos de servicios")
public class ServicePaymentController {

    private final IServicePaymentService servicePaymentService;

    public ServicePaymentController(IServicePaymentService servicePaymentService) {
        this.servicePaymentService = servicePaymentService;
    }

    @Operation(summary = "Obtener servicios disponibles", description = "Devuelve una lista de servicios disponibles desde un archivo JSON.")
    @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida exitosamente")
    @ApiResponse(responseCode = "500", description = "Error al leer los servicios")
    @GetMapping("/available-services")
    public ResponseEntity<List<Map<String, Object>>> getServices() {
        try {
            ClassPathResource jsonResource = new ClassPathResource("services.json");
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> services = objectMapper.readValue(jsonResource.getInputStream(), List.class);
            return ResponseEntity.ok(services);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Realizar un pago de servicio", description = "Registra un pago de servicio para un usuario específico.")
    @ApiResponse(responseCode = "200", description = "Pago registrado con éxito")
    @ApiResponse(responseCode = "400", description = "Error en el pago")
    @PostMapping("/pay/{userId}")
    public ResponseEntity<String> payService(@PathVariable Long userId, @RequestBody ServicePayment servicePayment) {
        try {
            ServicePayment payment = servicePaymentService.payService(userId, servicePayment);
            return ResponseEntity.ok("Pago de servicio registrado con éxito. ID: " + payment.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en el pago: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener todos los pagos de servicio", description = "Devuelve una lista de todos los pagos de servicio registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ServicePayment>> findAll() {
        List<ServicePayment> payments = servicePaymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Buscar un pago de servicio por ID", description = "Devuelve los detalles de un pago de servicio por su ID.")
    @ApiResponse(responseCode = "200", description = "Pago de servicio encontrado")
    @ApiResponse(responseCode = "404", description = "Pago de servicio no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ServicePayment> findById(@PathVariable Long id) {
        Optional<ServicePayment> servicePayment = servicePaymentService.findById(id);
        return servicePayment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un pago de servicio", description = "Modifica los detalles de un pago de servicio existente.")
    @ApiResponse(responseCode = "200", description = "Pago de servicio actualizado con éxito")
    @ApiResponse(responseCode = "400", description = "Error: Pago de servicio no encontrado")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ServicePayment servicePayment) {
        Optional<ServicePayment> existingPayment = servicePaymentService.findById(id);
        if (existingPayment.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Pago de servicio no encontrado!");
        }

        ServicePayment updatedPayment = existingPayment.get();
        updatedPayment.setAmount(servicePayment.getAmount());
        updatedPayment.setCompanyName(servicePayment.getCompanyName());
        updatedPayment.setServiceCode(servicePayment.getServiceCode());
        updatedPayment.setServiceType(servicePayment.getServiceType());

        servicePaymentService.payService(id, updatedPayment);
        return ResponseEntity.ok("Pago de servicio actualizado con éxito.");
    }

    @Operation(summary = "Eliminar un pago de servicio", description = "Elimina un pago de servicio por su ID.")
    @ApiResponse(responseCode = "200", description = "Pago de servicio eliminado con éxito")
    @ApiResponse(responseCode = "404", description = "Pago de servicio no encontrado")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (servicePaymentService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        servicePaymentService.deleteById(id);
        return ResponseEntity.ok("Pago de servicio eliminado con éxito.");
    }
}
