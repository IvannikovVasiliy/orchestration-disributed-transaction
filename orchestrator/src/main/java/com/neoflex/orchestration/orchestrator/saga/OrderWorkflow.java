package com.neoflex.orchestration.orchestrator.saga;

import com.neoflex.orchestration.orchestrator.step.WorkflowStep;

import java.util.List;

public class OrderWorkflow implements Workflow {

    public OrderWorkflow(List<WorkflowStep> steps) {
        this.steps = steps;
    }

    private final List<WorkflowStep> steps;

    @Override
    public List<WorkflowStep> getSteps() {
        return this.steps;
    }
}
