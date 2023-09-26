package com.neoflex.orchestration.order.service;

import com.neoflex.orchestration.order.domain.dto.OrchestratorResponseDto;
import reactor.core.publisher.Mono;

public interface UpdateService {
    Mono<Void> update(OrchestratorResponseDto orchestratorResponse);
}
