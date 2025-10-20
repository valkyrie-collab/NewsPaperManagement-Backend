package com.bytetrio.financial.model;

public class DeliveryFinancialDTO {
    private String id;
    private String deliveryId;
    private int totalDelivery;
    private double totalValueDelivery;
    private float commition;
    private boolean stillDelivery;
    
    public String getId() {
        return id;
    }
    public DeliveryFinancialDTO setId(String id) {
        this.id = id;
        return this;
    }
    public String getDeliveryId() {
        return deliveryId;
    }
    public DeliveryFinancialDTO setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }
    public int getTotalDelivery() {
        return totalDelivery;
    }
    public DeliveryFinancialDTO setTotalDelivery(int totalDelivery) {
        this.totalDelivery = totalDelivery;
        return this;
    }
    public double getTotalValueDelivery() {
        return totalValueDelivery;
    }
    public DeliveryFinancialDTO setTotalValueDelivery(double totalValueDelivery) {
        this.totalValueDelivery = totalValueDelivery;
        return this;
    }
    public float getCommition() {
        return commition;
    }
    public DeliveryFinancialDTO setCommition(float commition) {
        this.commition = commition;
        return this;
    }
    public boolean isStillDelivery() {
        return stillDelivery;
    }
    public DeliveryFinancialDTO setStillDelivery(boolean stillDelivery) {
        this.stillDelivery = stillDelivery;
        return this;
    }
    
}
