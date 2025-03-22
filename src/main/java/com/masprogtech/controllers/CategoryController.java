package com.masprogtech.controllers;

import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categoria", description = "Endpoints para gerenciar Categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Listar todas as categorias", description = "Listar todas as categorias (apenas Admin)." +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name ="security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Todas as categorias listadas com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (não é Admin)")
            })
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(Authentication authentication) {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @Operation(summary = "Registrar categoria", description = "Registrar uma nova categoria (apenas Admin)"+
                    "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name ="security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria registrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (não é Admin)")
            })
    @PostMapping("/register")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO, Authentication authentication) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Deletar categoria", description = "Deletar uma categoria existente (apenas Admin)"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name ="security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (não é Admin)")
            })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId, Authentication authentication) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok().body(new MessageResponse("Categoria excluída com sucesso", true));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage(), false));
        }
    }

    @Operation(summary = "Atualizar categoria", description = "Atualizar uma categoria existente (apenas Admin)"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name ="security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (não é Admin)")
            })
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                                      @PathVariable Long categoryId,
                                                      Authentication authentication) {
        CategoryDTO updateCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
        return new ResponseEntity<>(updateCategoryDTO, HttpStatus.OK);
    }
}