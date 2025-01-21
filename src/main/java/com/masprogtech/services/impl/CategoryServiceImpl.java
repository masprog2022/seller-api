package com.masprogtech.services.impl;

import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.entities.Category;
import com.masprogtech.exceptions.APIException;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.repositories.CategoryRepository;
import com.masprogtech.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                    category.getUpdateAt()
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

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }
}
