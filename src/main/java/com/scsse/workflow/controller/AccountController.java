package com.scsse.workflow.controller;

import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.service.AccountService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultCode;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    @Autowired
    public AccountController(AccountService accountService,UserService userService){

        this.accountService = accountService;
        this.userService = userService;
    }
    @RequiresRoles("deviceManager")
    @PostMapping("/account")
    public Result createOneAccount(@RequestBody Account account){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        account.setSubmitUser(user);
        account.setSubmitTime(new Date());
        account.setStatus(0);
        return ResultUtil.success(accountService.createAccount(account));
    }

    @GetMapping("/account/{accountId}")
    public Result getOneAccount(@PathVariable Integer accountId){
        return ResultUtil.success(accountService.getOneAccount(accountId));
    }

    @GetMapping("/account/all")
    public Result getAllAccount(){
        return ResultUtil.success(accountService.findAllAccount());
    }

    //只能删除未通过审核或审核不通过的账单
    @DeleteMapping("/account/{accountId}")
    public Result deleteAccount(@PathVariable Integer accountId){
      Boolean result=  accountService.deleteAccount(accountId);

      if(!result)
          return ResultUtil.error(ResultCode.CODE_503);
        return ResultUtil.success();
    }

    //通过设备Id来查找账单

    @GetMapping("/account/device/{deviceId}")
    public Result findAccountByDeviceId(@PathVariable Integer deviceId){
        return ResultUtil.success(accountService.findAccountByDeviceId(deviceId));

    }

    //通过审核人Id来查找其审核的账单
    @GetMapping("/account/checkUser/{checkId}")
    public Result findAccountByCheckUser(@PathVariable Integer checkId){
        return ResultUtil.success(accountService.findAccountByCheckUser(checkId));
    }

    //通过提交人Id来查询其提交的订单
    @GetMapping("/account/submitUser/{submitId}")
    public Result findAccountBySubmitUser(@PathVariable Integer submitId){
        return ResultUtil.success(accountService.findAccountBySubmit(submitId));
    }

    //根据账单状态来查询账单

    @GetMapping("/account/status")
    public Result findAccountByStatus(@RequestParam(required = true) Integer status)
    {
        return ResultUtil.success(accountService.findAccountByStatus(status));
    }
    //审核账单并通过
    @RequiresRoles("fundManager")
    @PostMapping("/account/pass/{accountId}")
    public Result checkAccountAndPass(@PathVariable Integer accountId){
        return ResultUtil.success(accountService.checkAccountAndPass(accountId));
    }

    //审核账单并且不通过
    @RequiresRoles("fundManager")
    @PostMapping("/account/noPass/{accountId}")
    public Result checkAccountAndNoPass(@PathVariable Integer accountId){
        return ResultUtil.success(accountService.checkAccountAndNoPass(accountId));
    }
    //分别查询支出账单和收益账单

    @GetMapping("/account/isExpense")
    public Result findAccountByStatus(@RequestParam(required = true) Boolean isExpense)
    {
        return ResultUtil.success(accountService.findAccountByIsExpense(isExpense));
    }

}
