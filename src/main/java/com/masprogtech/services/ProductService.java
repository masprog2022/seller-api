package com.masprogtech.services;

import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.payload.ProductResponse;
import jakarta.transaction.Transactional;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDto);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize,
                                   String sortBy, String sortOrder);


    MessageResponse deleteProduct(Long productId);

    ProductDTO updateProduct(Long productId, ProductDTO productDto);

}
