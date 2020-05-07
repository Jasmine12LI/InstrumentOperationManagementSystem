package com.scsse.workflow.controller;


import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.handler.WrongUsageException;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultCode;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }



    @GetMapping("/user/{userId}")
    public Result getUser(@PathVariable Integer userId){

        return ResultUtil.success(userService.getUser(userId));
    }

    //修改信息
    @PutMapping("/user/update")
    public Result updateUserInfo(@RequestBody User user) {
        return ResultUtil.success(userService.updateUser(user));
    }

    @RequiresRoles("admin")
    @DeleteMapping("/user/{userId}")
    public Result deleteUser(@PathVariable Integer userId)
    {
        userService.deleteUserById(userId);
        return ResultUtil.success();
    }

    @RequiresRoles("admin")
    @PostMapping("/user")
    public Result createUser(@RequestBody User user){
        return ResultUtil.success(userService.createUser(user));
    }

    @PostMapping("/user/login")
    public Result login(@RequestParam String username,
                        @RequestParam  String password){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(true);
            try{
                currentUser.login(token);
            } catch (UnknownAccountException uae){
                System.out.println(uae.getMessage());
                return ResultUtil.error(ResultCode.CODE_504);
            } catch(IncorrectCredentialsException ice){
                System.out.println(ice.getMessage());
                return ResultUtil.error(ResultCode.CODE_505);
            } catch(LockedAccountException lae){
                return ResultUtil.error(ResultCode.CODE_506);
            } catch (AuthenticationException ae)
            {
                return ResultUtil.error(ResultCode.CODE_507);
            }
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user);
        System.out.println(user.getName());
        return ResultUtil.success(userService.getUser(user.getId()));
    }

    @PostMapping("/user/logout")
    public Result logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResultUtil.success();
    }

    @RequiresRoles("admin")
    @GetMapping("/user/all")
    public Result getAllUser(){
        return  ResultUtil.success(userService.findAllUser());
    }

    @RequiresRoles("admin")
    @GetMapping("/user/role/add")
    public Result addRole(@RequestParam Integer userId,@RequestParam Integer roleId){
        userService.addRole(userId,roleId);
        return  ResultUtil.success();
    }
    @RequiresRoles("admin")
    @GetMapping("/user/role/remove")
    public Result removeRole(@RequestParam Integer userId,@RequestParam Integer roleId){
        userService.remove(userId,roleId);
        return  ResultUtil.success();
    }
    @RequiresRoles("admin")
    @GetMapping("/user/role/all")
    public Result AllRole(){
        return  ResultUtil.success(userService.findAllRole());
    }

}
