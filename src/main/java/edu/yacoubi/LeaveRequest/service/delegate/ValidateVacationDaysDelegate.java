package edu.yacoubi.LeaveRequest.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("validateVacationDaysDelegate")
public class ValidateVacationDaysDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ValidateVacationDaysDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        logExecutionDetails(execution);
        validateVacationDays(execution);
    }

    private void validateVacationDays(DelegateExecution execution) {
        Object requestedObj = execution.getVariable("requestedVacationDays");
        Object remainingObj = execution.getVariable("remainingVacationDays");

        if (requestedObj == null || !(requestedObj instanceof Number)) {
            throw new BpmnError("VALIDATION_ERROR", "requestedVacationDays is invalid");
        }
        if (remainingObj == null || !(remainingObj instanceof Number)) {
            throw new BpmnError("VALIDATION_ERROR", "remainingVacationDays is invalid");
        }

        long requested = ((Number) requestedObj).longValue();
        long remaining = ((Number) remainingObj).longValue();

        if (requested < 0 || remaining < 0) {
            throw new BpmnError("VALIDATION_ERROR", "Vacation days cannot be negative");
        }

        log.info("Vacation days validated successfully: requested={}, remaining={}", requested, remaining);
    }

    private void logExecutionDetails(DelegateExecution execution) {
        log.info("Process details: activityName='{}', activityId='{}', processInstanceId='{}', executionId='{}', variables='{}'",
                execution.getCurrentActivityName(),
                execution.getCurrentActivityId(),
                execution.getProcessInstanceId(),
                execution.getId(),
                execution.getVariables());
    }
}
