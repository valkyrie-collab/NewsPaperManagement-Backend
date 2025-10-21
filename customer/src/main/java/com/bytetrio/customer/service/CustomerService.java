package com.bytetrio.customer.service;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bytetrio.customer.config.TokenConfig;
import com.bytetrio.customer.model.Customer;
import com.bytetrio.customer.model.CustomerDTO;
import com.bytetrio.customer.model.CustomerNameAddress;
import com.bytetrio.customer.model.CustomerUpdate;
import com.bytetrio.customer.model.Image;
import com.bytetrio.customer.model.ImageDTO;
import com.bytetrio.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerService {
    private CustomerRepository customerRepo;
    @Autowired
    private void setCustomerRepo(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private String doDecoding(String word) {return new String(Base64.getDecoder().decode(word));}

    public ResponseEntity<Boolean> checkCustomerPresent(String token) {
        return ResponseEntity.status(HttpStatus.OK).body(customerRepo.existsById(config.getUsername(token)));
    }

    public ResponseEntity<String> add(String token, String customerJsonDataString, MultipartFile imageFile) throws IOException {
        Customer customer = new ObjectMapper().readValue(doDecoding(customerJsonDataString), Customer.class);
        // System.out.println("Username: " + config.getUsername(token));
        customer.setId(config.getUsername(token));

        if (imageFile != null) {
            Image image = new Image().setData(imageFile.getBytes()).setName(imageFile.getOriginalFilename())
                .setType(imageFile.getContentType()).setCustomer(customer);
            customer.setImage(image);
        }

        customerRepo.save(customer);

        return customerRepo.existsById(customer.getId())? 
            ResponseEntity.status(HttpStatus.ACCEPTED).body("customer data added successfully..") : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("customer data is not add....");

    } 

    public ResponseEntity<List<String>> update(String token, List<CustomerUpdate> customerUpdates) {
        String id = config.getUsername(token);
        List<String> messages = new LinkedList<>();

        for (CustomerUpdate customerUpdate : customerUpdates) {

            switch (customerUpdate.getFieldName().toUpperCase()) {
                case "FIRSTNAME": {
                    messages.add(customerRepo.updateFirstName(customerUpdate.getUpdatedValue(), id) > 0?
                        customerUpdate.getFieldName() + ": update successful...." : customerUpdate.getFieldName() + ": not updated successfully....");
                        break;
                } case "SECONDNAME": {
                    messages.add(customerRepo.updateSecondName(customerUpdate.getUpdatedValue(), id) > 0?
                        customerUpdate.getFieldName() + ": update successful...." : customerUpdate.getFieldName() + ": not updated successfully....");
                        break;
                } case "BIO": {
                    messages.add(customerRepo.updateBio(customerUpdate.getUpdatedValue(), id) > 0?
                        customerUpdate.getFieldName() + ": update successful...." : customerUpdate.getFieldName() + ": not updated successfully....");
                        break;
                } case "PHONENUMBER": {    
                    messages.add(customerRepo.updatePhoneNumber(Long.parseLong(customerUpdate.getUpdatedValue()), id) > 0?
                        customerUpdate.getFieldName() + ": update successful...." : customerUpdate.getFieldName() + ": not updated successfully....");
                        break;
                } case "ADDRESS": {
                    messages.add(customerRepo.updateAddress(customerUpdate.getUpdatedValue(), id) > 0?
                        customerUpdate.getFieldName() + ": update successful...." : customerUpdate.getFieldName() + ": not updated successfully....");
                        break;
                } case "EMAIL": {
                    messages.add(customerRepo.updateEmail(customerUpdate.getUpdatedValue(), id) > 0?
                        customerUpdate.getFieldName() + ": update successful...." : customerUpdate.getFieldName() + ": not updated successfully....");
                        break;
                } default: {
                    messages.add(customerUpdate.getFieldName() + ": not updated successfully....");
                    break;
                }
            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(messages);

    } 

    @Transactional
    public ResponseEntity<CustomerDTO> find(String token) {
        String username = config.getUsername(token);
        Customer customer = customerRepo.findById(username).orElse(null);
        ImageDTO imageDTO = new ImageDTO();

        if (customer == null) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}

        if (customerRepo.checkImages(username) == null || customerRepo.checkImages(username) <= 0) {
            imageDTO = null;
        } else {
            Image image = customerRepo.getProfileImage(username);
            imageDTO.setData(image.getData()).setId(image.getId()).setName(image.getName()).setType(image.getType());
        }

        CustomerDTO customerDTO = new CustomerDTO().setAddress(customer.getAddress())
            .setBio(customer.getAddress()).setFirstName(customer.getFirstName())
            .setId(customer.getId()).setImageDTO(imageDTO)
            .setPhoneNumber(customer.getPhoneNumber()).setSecondName(customer.getSecondName());

        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);

    }

    public ResponseEntity<List<CustomerNameAddress>> getAllCustomers() {
        List<CustomerNameAddress> customers = customerRepo.getNameAddressPhoneNumber();
        
        return customers.isEmpty()? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) : 
            ResponseEntity.status(HttpStatus.OK).body(customers);

    }

    @Transactional
    public ResponseEntity<String> removeCustomer(String customerId) {
        customerId = doDecoding(customerId);

        if (!customerRepo.existsById(customerId)) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already deleted");}

        customerRepo.deleteById(customerId);

        return ResponseEntity.status(HttpStatus.OK).body("customer with ID: " + customerId + " has been deleted successfully");

    }
}
