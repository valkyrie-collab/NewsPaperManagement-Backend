package com.bytetrio.financial.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_financial")
public class CustomerFinancial {
    @Id
    private String Id;
    private String customerId;
    private String subscriptionTitle;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private float cost;
    private boolean cancel;
    

}
