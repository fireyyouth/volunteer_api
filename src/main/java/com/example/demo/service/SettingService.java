package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.entity.Setting;
import com.example.demo.repository.SettingRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    @Autowired
    private SettingRepository settingRepository;

    public Setting getSetting() {
        return settingRepository.findAll().get(0);
    }

    public Setting updateSetting(Setting settingInfo) {
        Setting setting = settingRepository.findAll().get(0);
        setting.setHostHourRequirement(settingInfo.getHostHourRequirement());
        return settingRepository.save(setting);
    }
}
