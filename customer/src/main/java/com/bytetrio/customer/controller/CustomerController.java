package com.bytetrio.customer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bytetrio.customer.model.CustomerDTO;
import com.bytetrio.customer.model.CustomerNameAddress;
import com.bytetrio.customer.model.CustomerUpdate;
import com.bytetrio.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService service;
    @Autowired
    private void setService(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/add-customer")
    public ResponseEntity<String> add(@RequestParam String token, 
        @RequestParam String customerJsonDataString, @RequestPart(required = false) MultipartFile imageData) throws IOException {
        return service.add(token, customerJsonDataString, imageData);
    }

    @PostMapping("/update-customer")
    public ResponseEntity<List<String>> update(@RequestParam String token, @RequestBody List<CustomerUpdate> customerUpdate) {
        return service.update(token, customerUpdate);
    }

    @GetMapping("/find-customer")
    public ResponseEntity<CustomerDTO> find(@RequestParam String token) {
        return service.find(token);
    }

    @GetMapping("/customer-name-address")
    public ResponseEntity<List<CustomerNameAddress>> customers() {
        return service.getAllCustomers();
    }

    @DeleteMapping("/delete-mapping")
    public ResponseEntity<String> removeCustomerById(@RequestParam String customerId) {
        return service.removeCustomer(customerId);
    }

    @GetMapping("/check-customer-details")
    public ResponseEntity<Boolean> checkCustomerPresent(@RequestParam String token) {
        return service.checkCustomer(token);
    }

}
