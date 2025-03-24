package com.masprogtech.services;

import com.masprogtech.dtos.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO addAddressToClient(Long clientId, AddressDTO addressDTO);
    List<AddressDTO> getAddressByClient(Long clientId);
}
