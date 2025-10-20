package com.bytetrio.financial.model;

import java.util.Date;
import java.util.List;

public class CustomerFinancialDTO {
    private String id;
    private String customerId;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private float cost;
    private boolean cancel;
    private List<SubscriptionDTO> subscriptionDTOs;

    public String getId() {
        return id;
    }
    public CustomerFinancialDTO setId(String id) {
        this.id = id;
        return this;
    }
    public String getCustomerId() {
        return customerId;
    }
    public CustomerFinancialDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }
    public List<SubscriptionDTO> getSubscriptionDTOs() {
        return subscriptionDTOs;
    }
    public CustomerFinancialDTO setSubscriptionDTOs(List<SubscriptionDTO> subscriptionDTOs) {
        this.subscriptionDTOs = subscriptionDTOs;
        return this;
    }
    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }
    public CustomerFinancialDTO setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
        return this;
    }
    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }
    public CustomerFinancialDTO setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
        return this;
    }
    public float getCost() {
        return cost;
    }
    public CustomerFinancialDTO setCost(float cost) {
        this.cost = cost;
        return this;
    }
    public boolean isCancel() {
        return cancel;
    }
    public CustomerFinancialDTO setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }
    
}
