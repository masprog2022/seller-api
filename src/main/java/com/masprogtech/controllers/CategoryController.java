package com.masprogtech.controllers;

import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Registrar categoria", description = "Registrar categoria",

            responses = {
                    @ApiResponse(responseCode = "201", description = "categoria registada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)))
            })
    @PostMapping("/register")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Deletar categoria", description = "Registrar categoria",

            responses = {
                    @ApiResponse(responseCode = "200", description = "categoria deletada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
            })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok().body(new MessageResponse("Categoria excluída com sucesso", true));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage(), false));
        }
    }

    @Operation(summary = "Actualizar categoria", description = "Actualizar categoria",

            responses = {
                    @ApiResponse(responseCode = "200", description = "categoria actualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)))
            })
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                                      @PathVariable Long categoryId){
      CategoryDTO updateCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
      return new ResponseEntity<>(updateCategoryDTO, HttpStatus.OK);
    }
}
