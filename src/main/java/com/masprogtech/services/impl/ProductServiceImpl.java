package com.masprogtech.services.impl;

import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.entities.Category;
import com.masprogtech.entities.Product;
import com.masprogtech.exceptions.APIException;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.repositories.CategoryRepository;
import com.masprogtech.repositories.ProductRepository;
import com.masprogtech.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

           if (productRepository.existsByNameAndCategoryId(productDto.getName(), categoryId)) {
               throw new APIException("Product already exists!");
           }

            Product product = modelMapper.map(productDto, Product.class);
            product.setCategory(category);
            product.setSpecialPrice(calculateSpecialPrice(product.getPrice(), product.getDiscount()));
            product.setCreatedAt(LocalDateTime.now());
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
    }

    private double calculateSpecialPrice(double price, double discount) {
        return price - ((discount * 0.01) * price);
    }

}
