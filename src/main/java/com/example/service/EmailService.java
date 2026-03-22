package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import com.example.entity.Order;
import com.example.entity.OrderItem;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

        public void sendOrderConfirmation(String toEmail, Order order) {

            StringBuilder emailText = new StringBuilder();

            emailText.append("Thank you for your order!\n\n");
            emailText.append("Order Details:\n");

            for (OrderItem item : order.getOrderItems()) {
                emailText.append("- ")
                         .append(item.getProduct().getName())
                         .append(" | Qty: ")
                         .append(item.getQuantity())
                         .append(" | Price: ₹")
                         .append(item.getPrice())
                         .append("\n");
            }

            emailText.append("\nTotal Amount: ₹")
                     .append(order.getTotalAmount());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Order Confirmation - Plant Nursery");
            message.setText(emailText.toString());

            mailSender.send(message);
        }
    }
