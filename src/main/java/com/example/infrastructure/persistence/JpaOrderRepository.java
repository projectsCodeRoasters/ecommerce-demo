package com.example.infrastructure.persistence;

import com.example.domain.Order;
import com.example.domain.OrderLine;
import com.example.domain.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// Único punto del sistema que conoce tanto el dominio (Order/OrderLine) como
// la representación JPA (OrderJpaEntity/OrderLineJpaEntity).
@Repository
public class JpaOrderRepository implements OrderRepository {

    @Autowired
    private SpringDataOrderJpaRepository springDataOrderJpaRepository;

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toEntity(order);
        OrderJpaEntity saved = springDataOrderJpaRepository.save(entity);
        return toDomain(saved);
    }

    private OrderJpaEntity toEntity(Order order) {
        List<OrderLineJpaEntity> lines = new ArrayList<>(order.getLines().stream()
                .map(line -> new OrderLineJpaEntity(line.getId(), line.getProduct(), line.getQuantity(), line.getUnitPrice()))
                .toList());
        return new OrderJpaEntity(order.getId(), order.getCustomer(), lines, order.getTotal(), order.getStatus());
    }

    private Order toDomain(OrderJpaEntity entity) {
        List<OrderLine> lines = entity.getLines().stream()
                .map(line -> new OrderLine(line.getId(), line.getProduct(), line.getQuantity(), line.getUnitPrice()))
                .toList();
        return new Order(entity.getId(), entity.getCustomer(), lines, entity.getTotal(), entity.getStatus());
    }
}
