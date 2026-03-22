package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CartDao;
import com.example.dao.CartItemDao;
import com.example.dao.CustomerDao;
import com.example.dao.ProductDao;
import com.example.dto.CartItemResponseDTO;
import com.example.dto.CartResponseDTO;
import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.entity.Customer;
import com.example.entity.Product;


@Service
public class CartService {

    @Autowired
    private CartDao cartRepository;

    @Autowired
    private CartItemDao cartItemRepository;

    @Autowired
    private ProductDao productRepository;

    @Autowired
    private CustomerDao customerRepository;


    public void addToCart(Long customerId, Long productId, Integer quantity) {

        //  Get Customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        //  Get or Create Cart
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    return cartRepository.save(newCart);
                });

        //  Get Product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        //  Check if product already exists in cart
        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existingItem.isPresent()) {
            //  If product already in cart → increase quantity
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setSubTotal(item.getQuantity() * product.getPrice());
            cartItemRepository.save(item);

        } else {
            //  If not present → create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setSubTotal(quantity * product.getPrice());
            cartItemRepository.save(cartItem);
        }

        // 5 Update Cart Total
        Double total = cart.getCartItems()
                .stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();

        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }
    
    public CartResponseDTO getCartByEmail(String  email) {

        Cart cart = cartRepository.findByCustomerUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(cart.getId());
        response.setTotalPrice(cart.getTotalPrice());
        response.setEmail(cart.getCustomer().getUser().getEmail());

        List<CartItemResponseDTO> itemDTOList =
                cart.getCartItems().stream().map(item -> {

                    CartItemResponseDTO dto = new CartItemResponseDTO();
                    dto.setId(item.getId());
                    dto.setProductId(item.getProduct().getId());
                    dto.setProductName(item.getProduct().getName());
                    dto.setPrice(item.getProduct().getPrice());
                    dto.setQuantity(item.getQuantity());
                    dto.setSubTotal(item.getSubTotal());
                    dto.setImage(item.getProduct().getImageData());
                    

                    return dto;
                }).toList();

        response.setItems(itemDTOList);

        return response;
    }
    
    public void deleteCartItem(String email, Long cartItemId) {

        Customer customer = customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        cartItemRepository.delete(item);

        // recalculate total
        Double total = cart.getCartItems()
                .stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();

        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }
}
