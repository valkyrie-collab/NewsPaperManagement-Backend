package com.bytetrio.financial.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface CustomerFeignController {
    @GetMapping("/customer/check-customer-details")
    public ResponseEntity<Boolean> checkCustomerPresent(@RequestParam String token);
}
