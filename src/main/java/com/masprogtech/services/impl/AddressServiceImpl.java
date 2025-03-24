package com.masprogtech.services.impl;

import com.masprogtech.dtos.AddressDTO;
import com.masprogtech.entities.Address;
import com.masprogtech.entities.User;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.repositories.AddressRepository;
import com.masprogtech.repositories.UserRepository;
import com.masprogtech.services.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }


    @Override
    public AddressDTO addAddressToClient(Long clientId, AddressDTO addressDTO) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", clientId));

        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setAddress(addressDTO.getAddress());
        address.setDescription(addressDTO.getDescription());
        address.setUser(client);

        Address savedAddress = addressRepository.save(address);

        return new AddressDTO(savedAddress);
    }

    @Override
    public List<AddressDTO> getAddressByClient(Long clientId) {

        List<Address> addresses = addressRepository.findByUserUserId(clientId);


        return addresses.stream()
                .map(address -> new AddressDTO(address.getId(), address.getAddress(), address.getDescription()))
                .collect(Collectors.toList());
    }
}
