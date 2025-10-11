package com.bytetrio.customer.model;

public class CustomerUpdate {
    private String fieldName;
    private String updatedValue;

    public String getFieldName() {return fieldName;}

    public CustomerUpdate setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getUpdatedValue() {return updatedValue;}

    public CustomerUpdate setUpdatedValue(String updatedValue) {
        this.updatedValue = updatedValue;
        return this;
    }
}
