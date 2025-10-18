package com.bytetrio.financial.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytetrio.financial.model.CustomerFinancial;
import com.bytetrio.financial.model.CustomerFinancialDTO;
import com.bytetrio.financial.model.DeliveryFinancial;
import com.bytetrio.financial.model.DeliveryFinancialDTO;
// import com.bytetrio.financial.model.Subscription;
import com.bytetrio.financial.service.FinancialService;

@RestController
@RequestMapping("/financial")
public class FinancialController {
    private FinancialService service;
    @Autowired
    private void setService(FinancialService service) {
        this.service = service;
    }

    @PostMapping("/add-customer-bill")
    public ResponseEntity<String> addCustomerBill(@RequestParam String token, @RequestParam String customerFinancialJsonString, @RequestParam String subscriptionJsonString) throws IOException {
        return service.addCustomerFinance(token, customerFinancialJsonString, subscriptionJsonString);
    }

    @DeleteMapping("/cancel-subscription")
    public ResponseEntity<String> cancelSubscription(@RequestParam String token, @RequestParam String customerId, @RequestParam String subscriptionId) {
        return service.cancelSubscription(token, customerId, subscriptionId);
    }

    @GetMapping("/generate-bill")
    public ResponseEntity<CustomerFinancialDTO> getBill(@RequestParam String token, @RequestParam String customerFinancialId) {
        return service.generateBill(token, customerFinancialId);
    }

    @PostMapping("/delivery-done")
    public ResponseEntity<String> addDelivery(@RequestParam String token, @RequestParam DeliveryFinancial deliveryFinancial, @RequestParam(required = false) String deliveryId) {
        return service.addDeliveryData(token, deliveryFinancial, deliveryId);
    }

    @GetMapping("/delivery-data")
    public ResponseEntity<DeliveryFinancialDTO> getDelivery(@RequestParam String token, @RequestParam String deliveryFinancialId) {
        return service.getDeliveryData(token, deliveryFinancialId);
    }

    @DeleteMapping("/remove-customer")
    public ResponseEntity<String> removeCustomerDetail(@RequestParam String token) {
        return service.deleteCustomerData(token);
    }

    @DeleteMapping("/remove-delivery")
    public ResponseEntity<String> removeDeliveryDetails(@RequestParam String token) {
        return service.deleteDeliveryFinancial(token);
    }

}
