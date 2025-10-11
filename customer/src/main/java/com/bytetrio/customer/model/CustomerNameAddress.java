package com.bytetrio.customer.model;

public class CustomerNameAddress {
    private String firstName;
    private String secondName;
    private String address;
    private long phoneNumber;

    public CustomerNameAddress(String firstName, String secondName, String address, long phoneNumber) {
        this.firstName = firstName; this.secondName = secondName; this.address = address; this.phoneNumber = phoneNumber;
    }

    public CustomerNameAddress() {}

    public String getFirstName() {return firstName;}

    public CustomerNameAddress setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {return secondName;}

    public CustomerNameAddress setSecondName(String secondName) {
        this.secondName = secondName;
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
