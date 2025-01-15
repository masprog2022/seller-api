package com.masprogtech.controllers;

import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categoria", description = "Endpoints para gerenciar Categorias" )
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Listar toda categoria", description = "Listar todas Categorias",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Todos categorias listados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)))
            })
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
}
