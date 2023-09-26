package com.neoflex.orchestration.order.repository;


import com.neoflex.orchestration.order.domain.entity.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderRepository extends ReactiveCrudRepository<PurchaseOrder, UUID> {
}
