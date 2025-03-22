package com.masprogtech.controllers;

import com.masprogtech.config.AppConstants;
import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.dtos.ProductNameDTO;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.payload.ProductResponse;
import com.masprogtech.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Produto", description = "Endpoints para gerenciar Produtos" )
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Adicionar Produto", description = "Adicionar Produto"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
            })
    @PostMapping("/{categoryId}/add")
    public ResponseEntity<ProductDTO> addProduct(@PathVariable Long categoryId,
                                                 @RequestBody ProductDTO productDTO)
                                                 {
        ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar Produto", description = "Actualizar Produto"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto actualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
            })
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @Operation(summary = "Remover Produto", description = "Remover Produto"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto removido com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
            })
    @DeleteMapping("/{productId}")
     public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long productId) {

        MessageResponse messageResponse = productService.deleteProduct(productId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messageResponse);
    }

    @Operation(summary = "Listar todos produtos", description = "Listar todos produtos",

            responses = {
                    @ApiResponse(responseCode = "200", description = "produtos listado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class)))
            })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllOrders() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Retornar o nome pelo id do Produto", description = "Retornar o nome pelo id do Produto",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Nome retornado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductNameDTO.class)))
            })
    @GetMapping("/productName/{productId}")
    public ProductNameDTO getProductName(@PathVariable Long productId) {
        return productService.getProductNameById(productId);
    }


}