package com.neoflex.orchestration.orchestrator.step;

import com.neoflex.orchestration.orchestrator.domain.enumcommon.WorkflowStepStatus;
import reactor.core.publisher.Mono;

public interface WorkflowStep {
    WorkflowStepStatus getStatus();
    Mono<Boolean> process();
    Mono<Boolean> revert();
}
