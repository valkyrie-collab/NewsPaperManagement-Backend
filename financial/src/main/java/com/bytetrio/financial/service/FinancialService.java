package com.bytetrio.financial.service;

import java.io.IOException;
// import java.time.LocalDate;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytetrio.financial.config.CustomerFeignController;
import com.bytetrio.financial.config.TokenConfig;
import com.bytetrio.financial.model.CustomerFinancial;
import com.bytetrio.financial.model.CustomerFinancialDTO;
import com.bytetrio.financial.model.DeliveryFinancial;
import com.bytetrio.financial.model.DeliveryFinancialDTO;
import com.bytetrio.financial.model.Subscription;
import com.bytetrio.financial.model.SubscriptionDTO;
import com.bytetrio.financial.repository.DeliveryRepository;
import com.bytetrio.financial.repository.FinancialRepository;
import com.bytetrio.financial.repository.SubscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FinancialService {
    private FinancialRepository financialRepository;
    @Autowired
    private void setFinancialRepository(FinancialRepository financialRepository) {
        this.financialRepository = financialRepository;
    }

    private DeliveryRepository deliveryRepository;
    @Autowired
    private void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {
        this.config = config;
    }

    private SubscriptionRepository subsRepo;
    @Autowired
    private void setSubsRepo(SubscriptionRepository subsRepo) {
        this.subsRepo = subsRepo;
    }

    private CustomerFeignController customerFeignController;
    @Autowired
    private void setCustomerFeignController(CustomerFeignController customerFeignController) {
        this.customerFeignController = customerFeignController;
    }

    private String doDecoding(String word) {return new String(Base64.getDecoder().decode(word));}

    private CustomerFinancialDTO giveCustomerFinancialDTO(CustomerFinancial financial) {
        return new CustomerFinancialDTO().setCancel(financial.isCancel()).setCost(financial.getCost())
            .setCustomerId(financial.getCustomerId()).setId(financial.getId())
            .setSubscriptionEndDate(financial.getSubscriptionEndDate())
            .setSubscriptionStartDate(financial.getSubscriptionStartDate());
    }

    @Transactional
    public ResponseEntity<String> addCustomerFinance(String token, String financialJsonString, String subscriptionJsonString) throws IOException {
        boolean isCustomer = config.isMember(token);
        String username = config.getUsername(token);
        Boolean response = customerFeignController.checkCustomerPresent(token).getBody();

        if (response != null && !response) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First add customer data");
        }

        CustomerFinancial financial = new ObjectMapper().readValue(doDecoding(financialJsonString), CustomerFinancial.class);
        Subscription subscription = new ObjectMapper().readValue(doDecoding(subscriptionJsonString), Subscription.class);
        boolean existsSubscription = subsRepo.existsById(subscription.getId());

        if (isCustomer && existsSubscription) {
            financial.setId(UUID.randomUUID().toString()).setSubscription(subscription).setCustomerId(username);
            financialRepository.save(financial);

            return financialRepository.existsById(financial.getId())?
                ResponseEntity.status(HttpStatus.ACCEPTED).body("Saved successfully") : 
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not saved properly");

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existsSubscription? "This is not member give member...." : "The subscription do not exist...");

    }

    @Transactional
    public ResponseEntity<String> cancelSubscription(String token, String subscriptionId) {
        boolean isMember = config.isMember(token);
        // System.out.println(isMember);
        boolean isManager = config.isManager(token);
        String customerId = config.getUsername(token);
        subscriptionId = doDecoding(subscriptionId);
        boolean isPresent = financialRepository.checkForParticularSubscription(customerId, subscriptionId);

        if (isManager || isMember) {
            
            if (isPresent) {
                int count = financialRepository.updateSubscription(subscriptionId, customerId);
                return count > 0? ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated successfully") : 
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("subscription was already canceled");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("there is no such subscription");
            }

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have authorization");
        }

    }

    @Transactional
    public ResponseEntity<CustomerFinancialDTO> generateBill(String token, String customerFinancialId) {
        String username = config.getUsername(token);
        customerFinancialId = doDecoding(customerFinancialId);
        boolean exists = financialRepository.checkParticularCustomerFinancial(username, customerFinancialId);
        // boolean subscriptionExist = financialRepository.checkSubscriptions(username);

        if (!exists ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        CustomerFinancial customerFinancial = financialRepository.getCustomerFinancial(username, customerFinancialId);
        CustomerFinancialDTO customerFinancialDTO = giveCustomerFinancialDTO(customerFinancial);

        List<SubscriptionDTO> subscriptionDTOs = new LinkedList<>();

            for (String subscriptionId : financialRepository.getSubscriptions(username)) {
                Subscription subscription = subsRepo.findById(subscriptionId).orElse(null);

                if(subscription == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }

                subscriptionDTOs.add(new SubscriptionDTO().setId(subscription.getId()).setTitle(subscription.getTitle()));
            }

            customerFinancialDTO.setSubscriptionDTOs(subscriptionDTOs);

        return ResponseEntity.status(HttpStatus.OK).body(customerFinancialDTO);

    }

    @Transactional
    public ResponseEntity<String> addDeliveryData(String token, DeliveryFinancial deliveryFinancial, String deliveryId) {
        boolean isDelivery = config.isDelivery(token);
        // boolean isDelivery = true;
        String username = config.getUsername(token);

        if (isDelivery) {

            if (deliveryId == null) {
                deliveryId = UUID.randomUUID().toString();
                deliveryRepository.save(deliveryFinancial.setId(deliveryId).setDeliveryId(username));
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("first delivery has been added");
            } else {
                deliveryId = doDecoding(deliveryId);
                DeliveryFinancial financial = deliveryRepository.findById(deliveryId).orElse(null);

                if (financial == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("so such delivery");
                }

                int numberOfDelivery = financial.getTotalDelivery() + deliveryFinancial.getTotalDelivery();
                double numberOfMoney = financial.getTotalValueDelivery() + deliveryFinancial.getTotalValueDelivery();
                int count = deliveryRepository.updateDeliveryFinance(deliveryId, numberOfDelivery, numberOfMoney);

                return count > 0? ResponseEntity.status(HttpStatus.ACCEPTED).body("updated successfully") : 
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update not possible");
            }

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your not delivery boy");

    }

    @Transactional
    public ResponseEntity<DeliveryFinancialDTO> getDeliveryData(String token, String deliveryFinancialId) {
        deliveryFinancialId = doDecoding(deliveryFinancialId);
        boolean isPresentDeliveryData = deliveryRepository.existsById(deliveryFinancialId);
        DeliveryFinancial deliveryFinancial = deliveryRepository.findById(deliveryFinancialId).orElse(null);

        if (isPresentDeliveryData && deliveryFinancial != null) {
            float commition = (float) deliveryFinancial.getTotalValueDelivery() * 0.025f;
            DeliveryFinancialDTO deliveryFinancialDTO = new DeliveryFinancialDTO().setCommition(commition).setDeliveryId(deliveryFinancial.getDeliveryId())
                .setId(deliveryFinancialId).setStillDelivery(deliveryFinancial.isStillDelivery()).setTotalDelivery(deliveryFinancial.getTotalDelivery())
                .setTotalValueDelivery(deliveryFinancial.getTotalValueDelivery());
                
            return ResponseEntity.status(HttpStatus.OK).body(deliveryFinancialDTO);

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    @Transactional
    public ResponseEntity<String> deleteCustomerData(String token) {
        String username = config.getUsername(token);
        boolean isCustomer = config.isMember(token);

        if (isCustomer) {

            if (financialRepository.checkForCustomerFinancial(username)) {
                financialRepository.deleteAllByCustomerId(username);
                
                return ResponseEntity.status(HttpStatus.OK).body("Deleted data successfully.....");
            
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already been deleted....");

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token....");

    }

    @Transactional
    public ResponseEntity<String> deleteDeliveryFinancial(String token) {
        String username = config.getUsername(token);
        boolean isDelivery = config.isDelivery(token);
        // boolean isDelivery = true;

        if (isDelivery) {

            if (deliveryRepository.checkDeliveryFinance(username)) {
                deliveryRepository.deleteAllByDeliveryId(username);
                
                return ResponseEntity.status(HttpStatus.OK).body("Deleted data successfully.....");
            
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already been deleted....");

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token....");

    }

    public ResponseEntity<String> insertSubscription(String token, String subscriptionJsonString) throws IOException {
        Subscription subscription = new ObjectMapper().readValue(doDecoding(subscriptionJsonString), Subscription.class);
        // String username = config.getUsername(token);
        boolean isManager = config.isManager(token);
        // boolean isManager = true;

        if (isManager) {
            subsRepo.save(subscription.setId(UUID.randomUUID().toString()));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("subscription saved successfully with id = " + subscription.getId());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token is not of manager....");

    }

    public ResponseEntity<String> removeSubscription(String token, String subscriptionId) {
        subscriptionId = doDecoding(subscriptionId);
        boolean isExist = subsRepo.existsById(subscriptionId);
        boolean isManager = config.isManager(token);
        // boolean isManager = true;

        if (isExist && isManager) {
            subsRepo.deleteById(subscriptionId);
            return ResponseEntity.status(HttpStatus.OK).body("subscription removed successfully");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("it was already been deleted");
    
    }

}
