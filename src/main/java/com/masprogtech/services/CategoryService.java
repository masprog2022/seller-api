package com.masprogtech.services;


import com.masprogtech.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategory();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);

}
