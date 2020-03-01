package com.scsse.workflow.controller;

import com.scsse.workflow.entity.model.Device;
import com.scsse.workflow.service.DeviceService;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hibernate.annotations.Parameter;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @RequiresRoles("admin")
    @PostMapping("/device")
    public Result createDevice(@RequestBody Device device){
        return ResultUtil.success(deviceService.createDevice(device));
    }
    @RequiresRoles("admin")
    @PutMapping("/device/{deviceId}")
    public  Result updateDeviceName(@RequestParam(required = true) String name, @PathVariable Integer deviceId){
        return ResultUtil.success(deviceService.updateDeviceName(deviceId,name));
    }

    @GetMapping("/device/{deviceId}")
    public Result getDevice(@PathVariable Integer deviceId){
        return ResultUtil.success(deviceService.getOne(deviceId));
    }

    @GetMapping("/device/all")
    public Result getAllDevice()
    {
        return ResultUtil.success(deviceService.findAllDevice());
    }

    @RequiresRoles("admin")
    @DeleteMapping("/device/{deviceId}")
    public Result deleteDevice(@PathVariable Integer deviceId){
        deviceService.deleteDeviceById(deviceId);
        return ResultUtil.success();
    }



}
