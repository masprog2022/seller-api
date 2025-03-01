package com.masprogtech.services.impl;


import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.dtos.ProductNameDTO;
import com.masprogtech.entities.Category;
import com.masprogtech.entities.Product;
import com.masprogtech.exceptions.APIException;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.payload.ProductResponse;
import com.masprogtech.repositories.CategoryRepository;
import com.masprogtech.repositories.ProductRepository;
import com.masprogtech.services.ImageService;
import com.masprogtech.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private static final String UPLOAD_DIR = "C:/uploads/";
    private final ModelMapper modelMapper;

    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

           if (productRepository.existsByNameAndCategoryId(productDto.getName(), categoryId)) {
               throw new APIException("Product already exists!");
           }

            Product product = modelMapper.map(productDto, Product.class);
            product.setCategory(category);
            product.setSpecialPrice(calculateSpecialPrice(product.getPrice(), product.getDiscount()));
            product.setCreatedAt(LocalDateTime.now());
            product.setIsActive(TRUE);


        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Transactional
    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize,
                                          String sortBy, String sortOrder
                                          ) {

        // Configura a ordenação
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Configura os detalhes da paginação
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        // Recupera todos os produtos sem aplicar filtros
        Page<Product> pageProducts = productRepository.findAll(pageDetails);

        // Transforma a lista de produtos em DTOs
        List<ProductDTO> productDTOS = pageProducts.getContent().stream()
                .map(product -> {
                    ProductDTO dto = modelMapper.map(product, ProductDTO.class);
                    if (product.getCategory() != null) {
                        dto.setCategoryName(product.getCategory().getName());
                    }
                    return dto;
                })
                .toList();

        // Preenche a resposta com os dados paginados
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;


    }

    @Transactional
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> orders = productRepository.findByIsActiveTrue();
        return orders.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MessageResponse deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        Category category = product.getCategory();
        if (category != null) {
            category.getProducts().remove(product);
        }

        product.setIsActive(false);
        productRepository.save(product);
        return new MessageResponse("Product successfully removed", true);
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDto) {

        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        if (productDto.getName() != null) {
            productFromDb.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            productFromDb.setDescription(productDto.getDescription());
        }
        if (productDto.getQuantity() != null) {
            productFromDb.setQuantity(productDto.getQuantity());
        }
        if (productDto.getDiscount() >= 0) {
            productFromDb.setDiscount(productDto.getDiscount());
            productFromDb.setSpecialPrice(calculateSpecialPrice(productFromDb.getPrice(), productDto.getDiscount()));
        }

        if (productDto.getPrice() > 0 || productDto.getPrice() == 0) {
            productFromDb.setPrice(productDto.getPrice());
            productFromDb.setSpecialPrice(calculateSpecialPrice(productDto.getPrice(), productFromDb.getDiscount()));
        }

        if (productDto.getCreatedAt() != null) {
            productFromDb.setCreatedAt(productDto.getCreatedAt());
        }

        productFromDb.setUpdatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductNameDTO getProductNameById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        return new ProductNameDTO(product.getName());
    }

    private double calculateSpecialPrice(double price, double discount) {
        return price - ((discount * 0.01) * price);
    }

    private String saveImageToLocal(MultipartFile image) {
        try {
            // Criar diretório se não existir
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Gerar um nome único para o arquivo
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Salvar o arquivo no diretório
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Retornar a URL do arquivo salvo
            return UPLOAD_DIR + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem no diretório local", e);
        }
    }

}
