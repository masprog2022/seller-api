package com.masprogtech.services;

import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDto);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize,
                                   String sortBy, String sortOrder);

}
