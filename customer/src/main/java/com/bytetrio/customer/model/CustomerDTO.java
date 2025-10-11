package com.bytetrio.customer.model;

public class CustomerDTO {
    private String id;
    private String firstName;
    private String secondName;
    private String bio;
    private String email;
    private long phoneNumber;
    private String address;
    private ImageDTO imageDTO;

    public String getId() {return id;}

    public CustomerDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {return email;}

    public CustomerDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {return firstName;}

    public CustomerDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {return secondName;}

    public CustomerDTO setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getBio() {return bio;}

    public CustomerDTO setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public String getAddress() {return address;}

    public CustomerDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public long getPhoneNumber() {return phoneNumber;}

    public CustomerDTO setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ImageDTO getImageDTO() {return imageDTO;}

    public CustomerDTO setImageDTO(ImageDTO imageDTO) {
        this.imageDTO = imageDTO;
        return this;
    }

}
