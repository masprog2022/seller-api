package com.masprogtech.services.impl;

import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.entities.Category;
import com.masprogtech.entities.Product;
import com.masprogtech.exceptions.APIException;
import com.masprogtech.exceptions.BusinessException;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.repositories.CategoryRepository;
import com.masprogtech.repositories.OrderItemRepository;
import com.masprogtech.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;


    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        List<CategoryDTO> categoriesDTOs = new ArrayList<>();

        for (Category category : categories) {
            boolean hasOrders = orderItemRepository.existsByProductCategoryId(category.getId());

            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    category.getActive(),
                    hasOrders,
                    category.getCreatedAt(),
                    category.getUpdatedAt()
            );

            categoriesDTOs.add(categoryDTO);
        }

        return categoriesDTOs;
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class);

        /*Category categoryFromdb = categoryRepository.findByName(category.getName());
        if (categoryFromdb != null) {
            throw new APIException("Category with the name " +category.getName() + " already exists !!!");
        }*/

        if(categoryRepository.existsCategoryByName(categoryDTO.getName())){
            throw new APIException("Product already exists!");
        }

        category.setActive(TRUE);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }

    @Transactional
    @Override
    public MessageResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // Verifica se a categoria tem produtos ativos
        if (category.getProducts().stream().anyMatch(Product::getIsActive)) {
            throw new BusinessException("Não é possível excluir a categoria, pois existem produtos ativos associados a ela.");
        }

        // Soft delete na categoria
        category.setActive(false);
        categoryRepository.save(category);

        return new MessageResponse("Category successfully deactivated", true);
    }

    @Transactional
    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category categoryFromDb = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        if(categoryDTO.getName() != null){
           categoryFromDb.setName(categoryDTO.getName());
        }

        if(categoryDTO.getDescription() != null){
            categoryFromDb.setDescription(categoryDTO.getDescription());
        }

        if (categoryDTO.getCreatedAt() != null) {
            categoryFromDb.setCreatedAt(categoryDTO.getCreatedAt());
        }


        if (categoryDTO.getUpdatedAt() != null) {
            categoryFromDb.setUpdatedAt(LocalDateTime.now());
        }

        Category savedCategory = categoryRepository.save(categoryFromDb);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }

}
