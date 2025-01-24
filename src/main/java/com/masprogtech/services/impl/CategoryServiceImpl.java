package com.masprogtech.services.impl;

import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.entities.Category;
import com.masprogtech.exceptions.APIException;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.repositories.CategoryRepository;
import com.masprogtech.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoriesDTOs = new ArrayList<>();

        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
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
        Category categoryFromdb = categoryRepository.findByName(category.getName());
        if (categoryFromdb != null) {
            throw new APIException("Category with the name " +category.getName() + " already exists !!!");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }

    @Transactional
    @Override
    public MessageResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);
        return new MessageResponse("Category successfully removed", true);
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
