package com.scsse.workflow.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scsse.workflow.entity.model.Team;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.service.TeamService;
import com.scsse.workflow.service.UserService;


@Controller
public class DemoController {
	@Autowired
	TeamService teamService;
	@Autowired
	UserService userService;
//	@Autowired
//	RoleService	roleService;
//	@Autowired
//	TopicService topicService;
//	@Autowired
//	ReplyService replyService;
//	@Autowired
//	AnswerService answerService;
//	@Autowired
//	ActivityService activityService;
//	@Autowired
//	RecruitService recruitService;
//	@Autowired
//	AccessService accessService;
	
	@RequestMapping("/")
	@ResponseBody
	public Object hello() {
		return "hello springboot~~";
	}
//	@GetMapping(value = "/get/team")
//	@ResponseBody
//	public Team showOne(Integer id) throws Exception {
//		Team team = teamService.getOne(1);
//		Set<User> users = team.getMembers();
//		System.out.println("size:"+users.size());
//
//		for(User u:users) {
//			System.out.println("userid: "+u.getId());
//		}
//		return teamService.getOne(1);
//	}
	@GetMapping(value = "/get/user")
	@ResponseBody
	public User showUser(Integer id) throws Exception {
		
		return userService.getOne(1);
	}
//	@GetMapping(value = "/get/role")
//	@ResponseBody
//	public Role showRole(Integer id) throws Exception {
//		
//		return roleService.getOne(1);
//	}
//	@GetMapping(value = "/get/access")
//	@ResponseBody
//	public Access showAccess(Integer id) throws Exception {
//		
//		return accessService.getOne(1);
//	}
//	@GetMapping(value = "/get/topic")
//	@ResponseBody
//	public Topic showTopic(Integer id) throws Exception {
//		
//		return topicService.getOne(1);
//	}
//	@GetMapping(value = "/get/reply")
//	@ResponseBody
//	public Reply showReply(Integer id) throws Exception {
//		
//		return replyService.getOne(1);
//	}
//	@GetMapping(value = "/get/answer")
//	@ResponseBody
//	public Answer showAns(Integer id) throws Exception {
//		
//		return answerService.getOne(1);
//	}
//	@GetMapping(value = "/get/activity")
//	@ResponseBody
//	public Activity showAct(Integer id) throws Exception {
//		
//		return activityService.getOne(1);
//	}
//	@GetMapping(value = "/get/recruit")
//	@ResponseBody
//	public Recruit showRecruit(Integer id) throws Exception {
//		
//		return recruitService.getOne(1);
//	}
}
