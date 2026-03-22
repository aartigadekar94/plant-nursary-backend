package com.example.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.CategaryDao;
import com.example.dao.ProductDao;
import com.example.dto.ProductRequestDTO;
import com.example.dto.ProductResponseDTO;
import com.example.dto.ProductUserResponceDTO;
import com.example.entity.Categary;
import com.example.entity.Product;

@Service
public class ProductService {

    private final ProductDao productRepository;
    private final CategaryDao categoryRepository;
    
    

    public ProductService(ProductDao productRepository,
    		CategaryDao categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    
    
    
    public ProductResponseDTO createProduct(ProductRequestDTO dto,MultipartFile file) {

        Categary category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
               System.out.println("hello");
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setDescription(dto.getDiscription());
        product.setImageName(file.getOriginalFilename());
        product.setImageType(file.getContentType());
        try {
			product.setImageData(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return convertToResponseDTO(savedProduct);
    }
    
    
    private ProductResponseDTO convertToResponseDTO(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageData());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategoryName(product.getCategory().getName());

        return dto;
    }
    
    private ProductUserResponceDTO convertToUserResponseDTO(Product product) {

    	ProductUserResponceDTO dto = new ProductUserResponceDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImageData());;
        dto.setDescription(product.getDescription());
        dto.setCategoryName(product.getCategory().getName());

        return dto;
    }
    
    
    
    public List<ProductUserResponceDTO> searchProducts(String keyword) {

        List<Product> products =
                productRepository.findByNameContainingIgnoreCase(keyword);

        return products.stream()
                .map(this::convertToUserResponseDTO)
                .toList();
    }
   

    
    
    public List<ProductResponseDTO> getAllAdminProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }
    
    public List<ProductUserResponceDTO> getAllUserProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::convertToUserResponseDTO)
                .toList();
    }
   

    
    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return convertToResponseDTO(product);
    }
    
    public List<ProductUserResponceDTO> getProductByCategory(Long categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }

        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(this::convertToUserResponseDTO)
                .toList();
    }

    
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO updatedProduct,MultipartFile file) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        Categary category = categoryRepository.findById(updatedProduct.getCategoryId()).get();

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        try {
			product.setImageData(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        product.setImageName(file.getName());
        product.setImageType(file.getContentType());
        product.setCategory(category);
        
         productRepository.save(product);
         
         return convertToResponseDTO(product);
    }
    
 
    public byte[] getProductImage(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return product.getImageData();
    }





}

