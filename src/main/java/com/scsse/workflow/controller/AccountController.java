package com.scsse.workflow.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Account;
import com.scsse.workflow.entity.model.Item;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.repository.AccountRepository;
import com.scsse.workflow.repository.DeviceRepository;
import com.scsse.workflow.repository.ItemRepository;
import com.scsse.workflow.service.AccountService;
import com.scsse.workflow.service.ItemService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultCode;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final ItemService itemService;
    private final AccountRepository accountRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public AccountController(AccountService accountService,UserService userService,
                             ItemService itemService,AccountRepository accountRepository,
                             ItemRepository itemRepository){

        this.accountService = accountService;
        this.userService = userService;
        this.itemService = itemService;
        this.accountRepository = accountRepository;
        this.itemRepository = itemRepository;

    }
    @RequiresRoles("deviceManager")
    @PostMapping("/account")
    public Result createOneAccount(@RequestBody Account account){
        System.out.println("正在添加账单");
        account.setSubmitTime(new Date());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        account.setSubmitUser(user);
        account.setSubmitTime(new Date());
        account.setStatus(0);
        Set<Item> newItems = new HashSet<Item>();
        for(Item i : account.getItems()){
             newItems.add(itemService.createItem(i));
        }
        Account newAccount = accountRepository.save(account);
        for(Item i : newItems){
            itemService.setAccount(i,account);
        }
        user.getAccounts().add(newAccount);
        userService.updateUser(user);
        return ResultUtil.success(accountService.updateAccount(newAccount));
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

    //通过提交人来查询其提交的订单
    @GetMapping("/account/submitUser")
    public Result findAccountBySubmitUser(@RequestParam Integer status){
        return ResultUtil.success(accountService.findAccountBySubmit(status));
    }

    //根据账单状态来查询账单

    @GetMapping("/account/status")
    public Result findAccountByStatus(@RequestParam(required = true) Integer status)
    {
        return ResultUtil.success(accountService.findAccountByStatus(status));
    }
    //审核账单并通过
    @RequiresRoles("fundManager")
    @PostMapping("/account/pass")
    public Result checkAccountAndPass(@RequestParam Integer accountId,@RequestParam String des){
        return ResultUtil.success(accountService.checkAccountAndPass(accountId,des));
    }

    //审核账单并且不通过
    @RequiresRoles("fundManager")
    @PostMapping("/account/noPass")
    public Result checkAccountAndNoPass(@RequestParam Integer accountId,@RequestParam String des){
        return ResultUtil.success(accountService.checkAccountAndNoPass(accountId,des));
    }

    @RequiresRoles("deviceManager")
    @PostMapping("/account/pdf")

    public Result printPdf(@RequestParam Integer accountId,HttpServletResponse response) throws IOException {
        File file = accountService.pringFile(accountId);
        return ResultUtil.success();
    }



}
