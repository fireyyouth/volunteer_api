package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Setting;
import com.example.demo.service.SettingService;

@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping("/")
    public Setting getSetting() {
        return settingService.getSetting();
    }

    @PutMapping("/")
    public Setting updateSetting(@RequestBody Setting settingInfo) {
        return settingService.updateSetting(settingInfo);
    }
}
