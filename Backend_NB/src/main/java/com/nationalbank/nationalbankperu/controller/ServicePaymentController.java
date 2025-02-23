package com.nationalbank.nationalbankperu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nationalbank.nationalbankperu.model.ServicePayment;
import com.nationalbank.nationalbankperu.service.IServicePaymentService;
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
public class ServicePaymentController {

    private final IServicePaymentService servicePaymentService;

    public ServicePaymentController(IServicePaymentService servicePaymentService) {
        this.servicePaymentService = servicePaymentService;
    }

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

    @PostMapping("/pay/{userId}")
    public ResponseEntity<String> payService(@PathVariable Long userId, @RequestBody ServicePayment servicePayment) {
        try {
            ServicePayment payment = servicePaymentService.payService(userId, servicePayment);
            return ResponseEntity.ok("Pago de servicio registrado con éxito. ID: " + payment.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en el pago: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicePayment>> findAll() {
        List<ServicePayment> payments = servicePaymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicePayment> findById(@PathVariable Long id) {
        Optional<ServicePayment> servicePayment = servicePaymentService.findById(id);
        return servicePayment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (servicePaymentService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        servicePaymentService.deleteById(id);
        return ResponseEntity.ok("Pago de servicio eliminado con éxito.");
    }
}
