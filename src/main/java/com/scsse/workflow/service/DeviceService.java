package com.scsse.workflow.service;

import com.scsse.workflow.entity.dto.DeviceDto;
import com.scsse.workflow.entity.model.Device;

import javax.transaction.Transactional;
import java.util.List;

public interface DeviceService {

@Transactional
    DeviceDto getOne(Integer deviceId);
    List<DeviceDto> findAllDevice();
    DeviceDto createDevice(Device device);
    DeviceDto updateDeviceName(Integer id, String name);
    void deleteDeviceById(Integer deviceId);

}
