package com.bytetrio.customer.model;

import jakarta.persistence.Lob;

public class ImageDTO {
    private int id;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    public int getId() {
        return id;
    }
    public ImageDTO setId(int id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public ImageDTO setName(String name) {
        this.name = name;
        return this;
    }
    public String getType() {
        return type;
    }
    public ImageDTO setType(String type) {
        this.type = type;
        return this;
    }
    public byte[] getData() {
        return data;
    }
    public ImageDTO setData(byte[] data) {
        this.data = data;
        return this;
    }
    
}
