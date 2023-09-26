package com.neoflex.orchestration.orchestrator.saga;

import com.neoflex.orchestration.orchestrator.step.WorkflowStep;

import java.util.List;

public interface Workflow {
    List<WorkflowStep> getSteps();
}
