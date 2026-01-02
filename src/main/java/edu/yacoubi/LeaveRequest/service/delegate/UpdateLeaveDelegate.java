package edu.yacoubi.LeaveRequest.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("updateLeaveDelegate")
public class UpdateLeaveDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(UpdateLeaveDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        logExecutionDetails(execution);
        updateLeave(execution);
    }

    private void updateLeave(DelegateExecution execution) {
        Long requestedVacationDays = (Long) execution.getVariable("requestedVacationDays");
        Long remainingVacationDays = (Long) execution.getVariable("remainingVacationDays");
        String leaveStatus = (String) execution.getVariable("leaveStatus");

        if (requestedVacationDays == null) {
            log.error("Missing variable: requestedVacationDays");
            throw new IllegalArgumentException("Variable requestedVacationDays is missing");
        }

        if (remainingVacationDays == null) {
            log.error("Missing variable: remainingVacationDays");
            throw new IllegalArgumentException("Variable remainingVacationDays is missing");
        }

        if (leaveStatus == null) {
            log.error("Missing variable: leaveStatus");
            throw new IllegalArgumentException("Variable leaveStatus is missing");
        }

        if (requestedVacationDays < 0 || remainingVacationDays < 0) {
            log.error("Vacation days cannot be negative: requested={}, remaining={}", requestedVacationDays, remainingVacationDays);
            throw new IllegalArgumentException("Vacation days cannot be negative");
        }

        long newRemaining = remainingVacationDays - requestedVacationDays;

        execution.setVariable("remainingVacationDays", newRemaining);
        execution.setVariable("leaveStatus", "Updated");

        log.info("Leave updated successfully: remainingVacationDays={}, leaveStatus=Updated", newRemaining);
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
