package edu.yacoubi.LeaveRequest.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Delegate to send an email when a leave/expense request is rejected by the manager.
 */
@Component("sendRejectionEmailDelegate")
public class SendRejectionEmailDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {


        System.out.println("Rejection email sent to: ");
    }
}
