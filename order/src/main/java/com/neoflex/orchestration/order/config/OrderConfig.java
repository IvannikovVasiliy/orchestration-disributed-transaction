package com.neoflex.orchestration.order.config;

import com.neoflex.orchestration.order.domain.dto.OrchestratorRequestDto;
import com.neoflex.orchestration.order.domain.dto.OrchestratorResponseDto;
import com.neoflex.orchestration.order.service.UpdateService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class OrderConfig {

    @Autowired
    public OrderConfig(UpdateService updateService) {
        this.updateService = updateService;
    }

    private UpdateService updateService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Sinks.Many<OrchestratorRequestDto> sink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Sinks.Many<OrchestratorResponseDto> sinkResponse() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<OrchestratorRequestDto> flux(Sinks.Many<OrchestratorRequestDto> sink) {
        return sink.asFlux();
    }

    @Bean
    public Flux<OrchestratorResponseDto> fluxResponse(Sinks.Many<OrchestratorResponseDto> sink) {
        return sink.asFlux();
    }

    @Bean
    public Supplier<Flux<OrchestratorRequestDto>> supplier() {
        var flux = flux(sink());
        return () -> flux;
    }

//    @Bean
//    public Consumer<Flux<OrchestratorResponseDto>> consumer() {
////        var fluxResponse = fluxResponse(sinkResponse());
////        return param -> fluxResponse
////                .doOnNext(a -> log.debug("Consuming: {}", a))
////                .flatMap(response -> updateService.update(response))
////                .subscribe();
//
//        return null;
//    }

    @Bean
    public Consumer<String> consumerBinding() {
        return s -> log.debug("Data consumed: {}", s);
    }
}
