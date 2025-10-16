package com.bytetrio.financial.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery_financial")
public class DeliveryFinancial {
    @Id
    private String id;
    private String deliveryId;
    private int totalDelivery;
    private double totalValueDelivery;
    private boolean stillDelivery;
    
    public String getId() {
        return id;
    }
    public DeliveryFinancial setId(String id) {
        this.id = id;
        return this;
    }
    public String getDeliveryId() {
        return deliveryId;
    }
    public DeliveryFinancial setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }
    public int getTotalDelivery() {
        return totalDelivery;
    }
    public DeliveryFinancial setTotalDelivery(int totalDelivery) {
        this.totalDelivery = totalDelivery;
        return this;
    }
    public double getTotalValueDelivery() {
        return totalValueDelivery;
    }
    public DeliveryFinancial setTotalValueDelivery(double totalValueDelivery) {
        this.totalValueDelivery = totalValueDelivery;
        return this;
    }
    public boolean isStillDelivery() {
        return stillDelivery;
    }
    public DeliveryFinancial setStillDelivery(boolean stillDelivery) {
        this.stillDelivery = stillDelivery;
        return this;
    }
    
}
