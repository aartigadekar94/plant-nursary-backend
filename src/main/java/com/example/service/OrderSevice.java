package com.example.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.example.dao.CartDao;
import com.example.dao.CustomerDao;
import com.example.dao.OrderDao;
import com.example.dao.OrderItemDao;
import com.example.dto.OrderItemResponseDTO;
import com.example.dto.OrderRequestDTO;
import com.example.dto.OrderResponseDTO;
import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderItem;

import jakarta.transaction.Transactional;

@Service
public class OrderSevice {

    private final OrderDao orderRepo;
    private final CartDao cartRepo;
    private final CustomerDao customerRepo;
    private final OrderItemDao orderItemRepo;
    private final EmailService emailService;

    public OrderSevice(OrderDao orderRepo,
    		CartDao cartRepo,
    		CustomerDao customerRepo,OrderItemDao orderItemRepo,EmailService emailService) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.customerRepo = customerRepo;
        this.orderItemRepo=orderItemRepo;
        this.emailService=emailService;
    }

    @Transactional
    public void placeOrder(String email,OrderRequestDTO Address) {

        // 1️⃣ Get Customer
        Customer customer = customerRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 2️⃣ Get Cart
        Cart cart = cartRepo.findByCustomerId(customer.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 3️⃣ Create Order
        Order order = new Order();
        order.setDelivaryAddress(Address.getAddress());
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setSubTotal(cartItem.getSubTotal());
            cartItem.setQuantity(cartItem.getQuantity()-1);

            total += cartItem.getSubTotal();

            orderItems.add(orderItem);
            orderItemRepo.save(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        orderRepo.save(order);
        emailService.sendOrderConfirmation(email, order);

        //  Clear Cart
        cart.getCartItems().clear();
    }

    public List<OrderResponseDTO> getMyOrders(String email) {

        Customer customer = customerRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Order> orderList =
                orderRepo.findByCustomerIdOrderByOrderDateDesc(customer.getId());

        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orderList) {

            OrderResponseDTO orderResponse = new OrderResponseDTO();
              //for each orderResponse from each order
            orderResponse.setCustomerEmail(email);
            orderResponse.setDate(order.getOrderDate());
            orderResponse.setOrderId(order.getId());
            orderResponse.setDeliveryAddress(order.getDelivaryAddress());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setTotalAmount(order.getTotalAmount());

            //  Convert OrderItem → OrderItemResponseDTO
            List<OrderItemResponseDTO> itemDTOList = new ArrayList<>();

            for (OrderItem item : order.getOrderItems()) {

                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();

                itemDTO.setId(item.getId());
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setPrice(item.getPrice());
                itemDTO.setQuantity(item.getQuantity());

                itemDTOList.add(itemDTO);
            }

            orderResponse.setOrderItems(itemDTOList);

            responseList.add(orderResponse);
        }

        return responseList;
    }

	public List<OrderResponseDTO> getAllOrders() {
		// TODO Auto-generated method stub
		List<Order> orderList=orderRepo.findAll();
		List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orderList) {

            OrderResponseDTO orderResponse = new OrderResponseDTO();
              //for each orderResponse from each order
            orderResponse.setCustomerEmail(order.getCustomer().getUser().getEmail());
            orderResponse.setDate(order.getOrderDate());
            orderResponse.setOrderId(order.getId());
            orderResponse.setDeliveryAddress(order.getDelivaryAddress());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setTotalAmount(order.getTotalAmount());

            //  Convert OrderItem → OrderItemResponseDTO
            List<OrderItemResponseDTO> itemDTOList = new ArrayList<>();

            for (OrderItem item : order.getOrderItems()) {

                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();

                itemDTO.setId(item.getId());
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setPrice(item.getPrice());
                itemDTO.setQuantity(item.getQuantity());

                itemDTOList.add(itemDTO);
            }

            orderResponse.setOrderItems(itemDTOList);

            responseList.add(orderResponse);
        }
		return responseList;
	}

	public String updateDelivaryStatus(Long id, String status) {
		// TODO Auto-generated method stub
		 Order order = orderRepo.findById(id)
		            .orElseThrow(() -> new RuntimeException("Order not found"));

		    order.setDelivaryAddress(status);

		    orderRepo.save(order);

		    return "Order status updated";
	}

	
}
