package com.scsse.workflow.service.impl;


import com.scsse.workflow.entity.model.Workflow;
import com.scsse.workflow.repository.WorkflowRepository;
import com.scsse.workflow.service.WorkflowService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:18
 */
@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkflowServiceImpl(ModelMapper modelMapper,WorkflowRepository workflowRepository) {
        this.modelMapper = modelMapper;
        this.workflowRepository = workflowRepository;
    }

    @Override
    public List<Workflow> findAllWorkflow() {
        return workflowRepository.findAll();
    }

    @Override
    public Workflow findWorkflowById(Integer workflowId) {
        return workflowRepository.findOne(workflowId);
    }

    @Override
    public List<Workflow> findAllWorkflowByTeamId(Integer teamId){
        return workflowRepository.findAllByTeam_Id(teamId);
    }
    @Override
    public List<Workflow> findAllCreatedWorkflow(Integer userId) {

        return workflowRepository.findAllByCreator_Id(userId);
    }

    @Override
    public Workflow createWorkflow(Workflow workflow) {
        return workflowRepository.save(workflow);
    }

    @Override
    public Workflow updateWorkflow(Workflow workflow) {
        Integer workflowId = workflow.getId();
        Workflow oldWorkflow = workflowRepository.findOne(workflowId);
        modelMapper.map(workflow, oldWorkflow);
        return workflowRepository.save(oldWorkflow);
    }

    @Override
    public void deleteWorkflowById(Integer workflowId) {
        workflowRepository.delete(workflowId);
    }

}
