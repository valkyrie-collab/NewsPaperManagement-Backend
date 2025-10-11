package com.bytetrio.customer.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String secondName;
    private String bio;
    private String email;
    private long phoneNumber;
    private String address;
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private Image image;

    public String getId() {return id;}

    public Customer setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {return firstName;}

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {return secondName;}

    public Customer setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getBio() {return bio;}

    public Customer setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public String getEmail() {return email;}

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {return address;}

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public long getPhoneNumber() {return phoneNumber;}

    public Customer setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Image getImage() {return image;}

    public Customer setImage(Image image) {
        this.image = image;
        return this;
    }

}
