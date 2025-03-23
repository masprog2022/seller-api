package com.masprogtech.dtos;

import com.masprogtech.entities.Address;

public class AddressDTO {
    private Long id;
    private String address;
    private String description;

    public AddressDTO() {
    }

    public AddressDTO(Long id, String address, String description) {
        this.id = id;
        this.address = address;
        this.description = description;
    }


    public AddressDTO(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.description = address.getDescription();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
