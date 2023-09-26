package com.neoflex.orchestration.orchestrator.service;

import com.neoflex.orchestration.orchestrator.domain.dtocommon.OrchestratorRequestDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.OrchestratorResponseDto;
import reactor.core.publisher.Mono;

public interface OrchestratorService {
    Mono<OrchestratorResponseDto> orderProduct(OrchestratorRequestDto orchestratorRequest);
}
