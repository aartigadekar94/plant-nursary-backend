package com.example.dto;



public class ProductUserResponceDTO {
	
	    private Long id;
	    private String name;
	    private Double price;
	  
	    private byte[] image;
	    private String description;
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
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public byte[] getImage() {
			return image;
		}
		public void setImage(byte[] image) {
			this.image = image;
		}
		public void setImageData(byte[] imageData) {
			// TODO Auto-generated method stub
			
		}
		
		
	    
	    
	

}
