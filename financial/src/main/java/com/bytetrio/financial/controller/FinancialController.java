package com.bytetrio.financial.controller;

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
import com.bytetrio.financial.model.Subscription;
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
    public ResponseEntity<String> addCustomerBill(@RequestParam String token, @RequestBody CustomerFinancial customerFinancial) {
        return null;
    }

    @DeleteMapping("/cancel-subscription")
    public ResponseEntity<String> cancelSubscription(@RequestParam String token) {
        return null;
    }

    @GetMapping("/generate-bill")
    public ResponseEntity<CustomerFinancialDTO> getBill(@RequestParam String token) {
        return null;
    }

    @PostMapping("/delivery-done")
    public ResponseEntity<String> addDelivery(@RequestParam String token, @RequestBody DeliveryFinancial deliveryFinancial) {
        return null;
    }

    @GetMapping("/all-delivery")
    public ResponseEntity<DeliveryFinancialDTO> getDelivery(@RequestParam String token) {
        return null;
    }

    @DeleteMapping("/remove-customer")
    public ResponseEntity<String> removeCustomerDetail(@RequestParam String token) {
        return null;
    }

    @DeleteMapping("/remove-delivery")
    public ResponseEntity<String> removeDeliveryDetails(@RequestParam String token) {
        return null;
    }

}
