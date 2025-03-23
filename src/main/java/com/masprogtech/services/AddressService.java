package com.masprogtech.services;

import com.masprogtech.dtos.AddressDTO;

public interface AddressService {

    AddressDTO addAddressToClient(Long clientId, AddressDTO addressDTO);
}
