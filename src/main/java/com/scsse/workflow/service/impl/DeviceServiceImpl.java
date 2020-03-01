package com.scsse.workflow.service.impl;

import com.scsse.workflow.entity.dto.DeviceDto;
import com.scsse.workflow.entity.model.Device;
import com.scsse.workflow.repository.DeviceRepository;
import com.scsse.workflow.service.DeviceService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final ModelMapper modelMapper;
    private final DtoTransferHelper dtoTransferHelper;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImpl(ModelMapper modelMapper,DtoTransferHelper dtoTransferHelper,
                             DeviceRepository deviceRepository){
        this.modelMapper = modelMapper;
        this.dtoTransferHelper = dtoTransferHelper;
        this.deviceRepository = deviceRepository;
    }
    @Override
    public DeviceDto getOne(Integer deviceId) {
        return dtoTransferHelper.transferToDeviceDto(deviceRepository.findOne(deviceId));
    }

    @Override
    public List<DeviceDto> findAllDevice() {
        return dtoTransferHelper.transferToListDto(deviceRepository.findAll(),eachItem -> dtoTransferHelper.transferToDeviceDto((Device) eachItem));
    }

    @Override
    public DeviceDto createDevice(Device device) {
        return dtoTransferHelper.transferToDeviceDto(deviceRepository.save(device));
    }

    @Override
    public DeviceDto updateDeviceName(Integer id, String name) {
        Device device = deviceRepository.findOne(id);
        device.setName(name);
        return dtoTransferHelper.transferToDeviceDto(deviceRepository.save(device));

    }

    @Override
    public void deleteDeviceById(Integer deviceId) {
        deviceRepository.deleteById(deviceId);
        return;

    }
}
