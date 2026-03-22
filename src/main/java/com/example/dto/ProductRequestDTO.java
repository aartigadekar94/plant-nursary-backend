package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductRequestDTO {
	    @NotNull(message = "Please provide Product name")
	    private String name;
	    @Positive(message = "Price cant be negative")
	    private Double price;
	    @PositiveOrZero
	    private Integer stockQuantity;
	    private String discription;
	    
	    @NotNull
	    private Long categoryId;
	    
	    
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
		
		public String getDiscription() {
			return discription;
		}
		public void setDiscription(String discription) {
			this.discription = discription;
		}
		
		
		public Long getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}
	    
	    

}
