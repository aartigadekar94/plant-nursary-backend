package com.example.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductRequestDTO;
import com.example.dto.ProductResponseDTO;
import com.example.dto.ProductUserResponceDTO;
import com.example.entity.Product;
import com.example.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    
    @PostMapping(value="admin/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDTO createProduct(@Valid  @RequestPart("pro") ProductRequestDTO dto,@RequestPart("file") MultipartFile file) {
//    	System.out.println(dto.getName());
        return productService.createProduct(dto,file);
    }

    
    
    @GetMapping
    public List<ProductUserResponceDTO> getAllProducts() {
        return productService.getAllUserProducts();
    }
    
    @GetMapping("/admin")
    public List<ProductResponseDTO> getAllAdminProducts() {
        return productService.getAllAdminProducts();
    }
    
    
    @GetMapping("cust/getByCategory/{categoryId}")
    public List<ProductUserResponceDTO> getAllProductsBycategory(@PathVariable Long categoryId) {
        return productService.getProductByCategory(categoryId);
    }
    
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductUserResponceDTO>> searchProducts(
            @RequestParam String keyword) {

        return ResponseEntity.ok(productService.searchProducts(keyword));
    }


    
    
    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }
    
    @PutMapping(value="admin/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO>  updateProduct(@PathVariable("id") Long id,
                                 @RequestPart("pro") ProductRequestDTO product,@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(productService.updateProduct(id, product,file));
    }
    
    @DeleteMapping("admin/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
    
//    @GetMapping("/image/{productId}")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long productId) {
//
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.valueOf(product.getImageType()))
//                .body(product.getImageData());
//    } 
    

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        byte[] image = productService.getProductImage(id);
       

        return ResponseEntity.ok()
                .body(image);
}
}





