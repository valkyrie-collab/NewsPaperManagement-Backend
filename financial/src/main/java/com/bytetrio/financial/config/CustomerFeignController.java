package com.bytetrio.financial.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("CUSTOMER")
public interface CustomerFeignController {

    @GetMapping("/customer/checkCustomer")
    public ResponseEntity<Boolean> checkCustomerPresent(@RequestParam String token);

}
