package com.masprogtech.services;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.dtos.ProductNameDTO;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.payload.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDto);

    List<ProductDTO> getAllProducts();

    MessageResponse deleteProduct(Long productId);
    ProductDTO updateProduct(Long productId, ProductDTO productDto);

    ProductNameDTO getProductNameById(Long productId);

}
