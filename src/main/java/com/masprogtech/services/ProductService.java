package com.masprogtech.services;

import com.masprogtech.dtos.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDto);
}
