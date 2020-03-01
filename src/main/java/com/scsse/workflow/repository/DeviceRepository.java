package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Device findDeviceById(Integer device_id);
    void deleteById(Integer deviceId);
}
