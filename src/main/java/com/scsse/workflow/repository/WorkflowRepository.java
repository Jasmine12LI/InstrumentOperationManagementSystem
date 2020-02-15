package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:08
 */
public interface WorkflowRepository extends JpaRepository<Workflow, Integer> {
    List<Workflow> findAllByTeam_Id(Integer teamId);
    List<Workflow> findAllByCreator_Id(Integer userId);
}
