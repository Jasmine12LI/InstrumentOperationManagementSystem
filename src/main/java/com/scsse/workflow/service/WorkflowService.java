package com.scsse.workflow.service;


import com.scsse.workflow.entity.model.Workflow;

import java.util.List;

public interface WorkflowService {
    List<Workflow> findAllWorkflow();

    Workflow findWorkflowById(Integer workflowId);

    List<Workflow> findAllWorkflowByTeamId(Integer teamId);

    List<Workflow>  findAllCreatedWorkflow(Integer userId);

    Workflow createWorkflow(Workflow workflow);

    Workflow updateWorkflow(Workflow workflow);

    void deleteWorkflowById(Integer workflowId);
}
