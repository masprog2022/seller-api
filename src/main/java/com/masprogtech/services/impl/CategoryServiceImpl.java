package com.masprogtech.services.impl;

import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.entities.Category;
import com.masprogtech.repositories.CategoryRepository;
import com.masprogtech.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoriesDTOs = new ArrayList<>();

        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getId(),
                    category.getName()
            );

            categoriesDTOs.add(categoryDTO);
        }

        return categoriesDTOs;

    }
}
