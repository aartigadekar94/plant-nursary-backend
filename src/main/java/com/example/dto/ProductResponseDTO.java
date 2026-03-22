package com.example.dto;

import jakarta.validation.constraints.Positive;

public class ProductResponseDTO {

	    @Positive(message = "Id should be ")
	    private Long id;
	    private String name;
	    private Double price;
	    private Integer stockQuantity;
	    private byte[] imageUrl;
	    private String categoryName;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public Integer getStockQuantity() {
			return stockQuantity;
		}
		public void setStockQuantity(Integer stockQuantity) {
			this.stockQuantity = stockQuantity;
		}
		
		
		public byte[] getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(byte[] bs) {
			this.imageUrl = bs;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
	    
	    

}
