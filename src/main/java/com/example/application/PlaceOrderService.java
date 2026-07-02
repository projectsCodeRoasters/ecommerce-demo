package com.example.application;

import com.example.Customer;
import com.example.CustomerRepository;
import com.example.Product;
import com.example.ProductRepository;
import com.example.application.PlaceOrderCommand.OrderLineCommand;
import com.example.domain.Order;
import com.example.domain.OrderLine;
import com.example.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrderService implements PlaceOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Order placeOrder(PlaceOrderCommand command) {
        Customer customer = customerRepository.findById(command.customerId()).get();
        Order order = new Order(customer);

        for (OrderLineCommand lineCommand : command.lines()) {
            Product product = productRepository.findById(lineCommand.productId()).get();
            if (product.getStock() < lineCommand.quantity()) {
                throw new RuntimeException("Not enough stock for " + product.getName());
            }
            order.addLine(new OrderLine(product, lineCommand.quantity(), product.getPrice()));
        }
        order.confirm();

        return orderRepository.save(order);
    }
}
