package com.bytetrio.customer.model;

public class CustomerNameAddress {
    private String name;
    private String address;
    private long phoneNumber;

    public CustomerNameAddress(String name, String address, long phoneNumber) {
        this.name = name; this.address = address; this.phoneNumber = phoneNumber;
    }

    public CustomerNameAddress() {}

    public String getName() {return name;}

    public CustomerNameAddress setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {return address;}

    public CustomerNameAddress setAddress(String address) {
        this.address = address;
        return this;
    }

    public long getPhoneNumber() {return phoneNumber;}

    public CustomerNameAddress setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
