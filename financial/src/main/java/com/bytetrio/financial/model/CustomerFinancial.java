package com.bytetrio.financial.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
// import jakarta.persistence.OneToMany;

@Entity
@Table(name = "customer_financial")
public class CustomerFinancial {
    @Id
    private String id;
    private String customerId;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private float cost;
    private boolean cancel = false;
    @ManyToMany(mappedBy = "customerFinancials", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;
    
    public String getId() {
        return id;
    }
    public CustomerFinancial setId(String id) {
        this.id = id;
        return this;
    }
    public String getCustomerId() {
        return customerId;
    }
    public CustomerFinancial setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }
    public List<Subscription> getSubscription() {
        return subscriptions;
    }
    public CustomerFinancial setSubscription(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
        return this;
    }
    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }
    public CustomerFinancial setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
        return this;
    }
    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }
    public CustomerFinancial setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
        return this;
    }
    public float getCost() {
        return cost;
    }
    public CustomerFinancial setCost(float cost) {
        this.cost = cost;
        return this;
    }
    public boolean isCancel() {
        return cancel;
    }
    public CustomerFinancial setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }

}
