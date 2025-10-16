package com.bytetrio.financial.service;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bytetrio.financial.config.TokenConfig;
import com.bytetrio.financial.model.CustomerFinancial;
import com.bytetrio.financial.model.CustomerFinancialDTO;
import com.bytetrio.financial.model.Subscription;
import com.bytetrio.financial.model.SubscriptionDTO;
import com.bytetrio.financial.repository.FinancialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FinancialService {
    private FinancialRepository financialRepository;
    @Autowired
    private void setFinancialRepository(FinancialRepository financialRepository) {
        this.financialRepository = financialRepository;
    }

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {
        this.config = config;
    }

    private String doDecoding(String word) {return new String(Base64.getDecoder().decode(word));}

    private CustomerFinancialDTO giveCustomerFinancialDTO(CustomerFinancial financial) {
        return new CustomerFinancialDTO().setCancel(financial.isCancel()).setCost(financial.getCost())
            .setCustomerId(financial.getCustomerId()).setId(financial.getId())
            .setSubscriptionEndDate(financial.getSubscriptionEndDate())
            .setSubscriptionStartDate(financial.getSubscriptionStartDate());
    }

    public ResponseEntity<String> addCustomerFinance(String token, String financialJsonString, String subscriptionJsonString) throws IOException {
        boolean isCustomer = config.isMember(token);
        String username = config.getUsername(token);
        CustomerFinancial financial = new ObjectMapper().readValue(financialJsonString, CustomerFinancial.class);
        Subscription subscription = new ObjectMapper().readValue(subscriptionJsonString, Subscription.class);
        boolean existsSubscription = financialRepository.checkSubscriptions(username);

        if (isCustomer || !existsSubscription) {
            financial.setId(UUID.randomUUID().toString()).setCustomerId(username);
            subscription.setCustomerFinancial(List.of(financial));
            financial.setSubscription(List.of(subscription));
            financialRepository.save(financial);
            return financialRepository.existsById(financial.getId())?
                ResponseEntity.status(HttpStatus.ACCEPTED).body("subscription added successfully") : 
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not accepted");
        } else if (isCustomer || existsSubscription) {
            subscription.setId(UUID.randomUUID().toString());
            financialRepository.insetSubscriptions(username, subscription.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Added subscription");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This is not member give member....");

    }

    public ResponseEntity<String> cancelSubscription(String token, String customerId, String subscriptionId) {
        boolean isMember = config.isMember(token);
        boolean isManager = config.isManager(token);
        customerId = doDecoding(customerId);
        subscriptionId = doDecoding(subscriptionId);

        if (isManager) {
            boolean isPresent = financialRepository.existsById(customerId);
            
            if (isPresent) {
                int count = financialRepository.updateSubscription(subscriptionId, customerId);
                return count > 0? ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated successfully") : 
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("subscription was already canceled");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there is no such subscription");
            }

        } else if (isMember) {
            boolean isPresent = financialRepository.existsById(config.getUsername(token));
            String username = config.getUsername(token);
            
            if (isPresent) {
                int count = financialRepository.updateSubscription(subscriptionId, username);
                return count > 0? ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated successfully") : 
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("subscription was already canceled");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there is no such subscription");
            }

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have authorization");
        }

    }

    public ResponseEntity<CustomerFinancialDTO> generateBill(String token, String customerFinancialId) {
        String username = config.getUsername(token);
        customerFinancialId = doDecoding(customerFinancialId);
        boolean exists = financialRepository.checkParticularCustomerFinancial(username, customerFinancialId);
        boolean subscriptionExist = financialRepository.checkSubscriptions(username);

        if (!exists ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        CustomerFinancial customerFinancial = financialRepository.getCustomerFinancial(username, customerFinancialId);
        CustomerFinancialDTO customerFinancialDTO = giveCustomerFinancialDTO(customerFinancial);

        if (subscriptionExist) {
            List<SubscriptionDTO> subscriptionDTOs = new LinkedList<>();

            for (Subscription subscription : financialRepository.getSubscriptions(username)) {
                subscriptionDTOs.add(new SubscriptionDTO().setId(subscription.getId()).setTitle(subscription.getTitle()));
            }

            customerFinancialDTO.setSubscriptionDTOs(subscriptionDTOs);
        } else {
            customerFinancialDTO.setSubscriptionDTOs(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerFinancialDTO);

    }

}
