package com.neoflex.orchestration.orchestrator.config;

import com.neoflex.orchestration.orchestrator.domain.dtocommon.OrchestratorRequestDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.OrchestratorResponseDto;
import com.neoflex.orchestration.orchestrator.service.OrchestratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import org.modelmapper.ModelMapper;

import java.util.function.Function;

@Configuration
@Slf4j
public class OrchestratorConfig {

    @Autowired
    public OrchestratorConfig(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDto>, Flux<OrchestratorResponseDto>> processor() {
        return flux -> flux
                .flatMap(dto -> orchestratorService.orderProduct(dto))
                .doOnNext(dto -> log.info("Status: {}", dto.getOrderStatus()));
    }
}
