package com.example.infrastructure.persistence;

import com.example.domain.Money;
import com.example.domain.Order;
import com.example.domain.OrderId;
import com.example.domain.OrderLine;
import com.example.domain.OrderRepository;
import com.example.domain.Quantity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// Único punto del sistema que conoce tanto el dominio (Order/OrderLine, con
// sus value objects) como la representación JPA (OrderJpaEntity/
// OrderLineJpaEntity, con tipos primitivos).
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
                .map(line -> new OrderLineJpaEntity(
                        line.getId(), line.getProduct(), line.getQuantity().value(), line.getUnitPrice().amount()))
                .toList());
        Long id = order.getId().map(OrderId::value).orElse(null);
        return new OrderJpaEntity(id, order.getCustomer(), lines, order.getTotal().amount(), order.getStatus());
    }

    private Order toDomain(OrderJpaEntity entity) {
        List<OrderLine> lines = entity.getLines().stream()
                .map(line -> new OrderLine(
                        line.getId(), line.getProduct(), new Quantity(line.getQuantity()), Money.of(line.getUnitPrice())))
                .toList();
        OrderId id = new OrderId(entity.getId());
        return new Order(id, entity.getCustomer(), lines, Money.of(entity.getTotal()), entity.getStatus());
    }
}
