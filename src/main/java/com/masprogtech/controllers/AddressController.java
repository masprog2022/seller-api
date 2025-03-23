package com.masprogtech.controllers;


import com.masprogtech.dtos.AddressDTO;
import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.entities.User;
import com.masprogtech.exceptions.BusinessException;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.repositories.UserRepository;
import com.masprogtech.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Endereço", description = "Endpoints para gerenciar endereço")
public class AddressController {

    private final AddressService addressService;
    private final UserRepository userRepository;

    public AddressController(AddressService addressService, UserRepository userRepository) {
        this.addressService = addressService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Adicionar Endereço", description = "Adicionar endereço (apenas Client)"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENT'",
            security = @SecurityRequirement(name ="security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "endereço adicionado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (não é Client)")
            })
    @PostMapping
    public ResponseEntity<AddressDTO> addAddressToClient(@RequestBody AddressDTO addressDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        User client = userRepository.findByUsername(username);

        if (client == null) {
            throw new BusinessException("Cliente não encontrado");
        }

        AddressDTO savedAddressDTO = addressService.addAddressToClient(client.getUserId(), addressDTO);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }
}
