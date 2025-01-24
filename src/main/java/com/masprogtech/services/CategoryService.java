package com.masprogtech.services;


import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.payload.MessageResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategory();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    MessageResponse deleteCategory(Long categoryId);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

}
