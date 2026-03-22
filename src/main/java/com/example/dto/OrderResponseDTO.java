package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
	 private Long orderId;
	    private String deliveryAddress;
	    private Double totalAmount;
	    private String status;
	    private String delivaryStatus; 
	    private LocalDateTime date;
	    private String customerEmail;
	    private List<OrderItemResponseDTO> orderItems;
		public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public String getDeliveryAddress() {
			return deliveryAddress;
		}
		public void setDeliveryAddress(String deliveryAddress) {
			this.deliveryAddress = deliveryAddress;
		}
		public Double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public LocalDateTime getDate() {
			return date;
		}
		public void setDate(LocalDateTime date) {
			this.date = date;
		}
		public String getCustomerEmail() {
			return customerEmail;
		}
		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}
		public List<OrderItemResponseDTO> getOrderItems() {
			return orderItems;
		}
		public void setOrderItems(List<OrderItemResponseDTO> orderItems) {
			this.orderItems = orderItems;
		}
		public String getDelivaryStatus() {
			return delivaryStatus;
		}
		public void setDelivaryStatus(String delivaryStatus) {
			this.delivaryStatus = delivaryStatus;
		}
		
	    
	    


}
