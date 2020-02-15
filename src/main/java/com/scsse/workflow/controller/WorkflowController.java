package com.scsse.workflow.controller;

import com.scsse.workflow.entity.model.Team;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.entity.model.Workflow;
import com.scsse.workflow.handler.WrongUsageException;
import com.scsse.workflow.service.TeamService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.service.WorkflowService;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019/10/6 3:11 下午
 */
@RestController
public class WorkflowController {

    private final WorkflowService workflowService;
    private final TeamService teamService;
    private final UserUtil userUtil;

    @Autowired
    public WorkflowController(WorkflowService workflowService,TeamService teamService, UserUtil userUtil) {
        this.workflowService = workflowService;
        this.userUtil = userUtil;
        this.teamService = teamService;
    }

    /**
     * 查看某团队的工作进展
     * @param teamId 团队id
     * @return List{Workflow}
     */
    @GetMapping("/team/{teamId}/workflow")
    public Result getWorkProgressByTeam(@PathVariable() Integer teamId) {
        return ResultUtil.success(
                workflowService.findAllWorkflowByTeamId(teamId)
        );
    }

    /**
     * 获取某用户创建的个人工作进展
     *
     * @param userId 调用者的openid
     * @return List{Workflow}
     * <p>
     * e.g.
     * GET /user/1/workflow
     */
    @GetMapping("/user/{userId}/workflow")
    public Result getMyCreatedWorkProgress(@PathVariable() Integer userId) {
        return ResultUtil.success(
                workflowService.findAllCreatedWorkflow(userId)
        );
    }

    /**
     * 获取我加入的所有团队的工作进展
     *
     * @return List{Workflow}
     */
    @GetMapping("/team/workflow/all")
    public Result getMyJoinedWorkProgress() throws WrongUsageException {
        List<Workflow> workflows = new ArrayList<>();
        User user = userUtil.getLoginUser();
        Set<Team> myJoinedTeams = user.getJoinedTeam();
        for(Team team:myJoinedTeams){
            workflows.addAll(workflowService.findAllWorkflowByTeamId(team.getId()));
        }
        return ResultUtil.success(workflows);
    }

    @GetMapping("/workflow/{workflowId}")
    public Result getWorkflow(@PathVariable Integer workflowId) {
        return ResultUtil.success(
                workflowService.findWorkflowById(workflowId)
        );
    }

    @PutMapping("/workflow/{workflowId}")
    public Result updateWorkflow(@PathVariable Integer workflowId, @RequestBody Workflow workflow) throws Exception {
        workflow.setId(workflowId);
        workflow.setCreateTime(null);
        return ResultUtil.success(
                workflowService.updateWorkflow(workflow)
        );
    }

    @PostMapping("/workflow/{teamId}")
    public Result createWorkflow(@RequestBody Workflow workflow,@PathVariable("teamId")Integer teamId) {
        workflow.setCreateTime(new Date());
        workflow.setTeam(teamService.findTeam(teamId));
        workflow.setCreator(userUtil.getLoginUser());
        return ResultUtil.success(
                workflowService.createWorkflow(workflow)
        );
    }

    @DeleteMapping("/workflow/{workflowId}")
    public Result deleteTeam(@PathVariable Integer workflowId) {
        workflowService.deleteWorkflowById(workflowId);
        return ResultUtil.success();
    }


}
