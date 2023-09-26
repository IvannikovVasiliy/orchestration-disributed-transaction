package com.neoflex.orchestration.order.domain.entity;

import com.neoflex.orchestration.order.domain.enumeration.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
public class PurchaseOrder {

    public PurchaseOrder(UUID id, Long userId, Long productId, double price, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.orderStatus = orderStatus;
    }

    public PurchaseOrder() {
    }

    @Id
    private UUID id;
    private Long userId;
    private Long productId;
    private double price;
    private OrderStatus orderStatus;
}
