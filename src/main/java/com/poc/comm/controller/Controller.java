package com.poc.comm.controller;

import com.poc.comm.beans.Usage;
import com.poc.comm.models.dto.QuotaDto;
import com.poc.comm.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    ServiceImpl service;

    @GetMapping("/ping")
    public String getPing() {
        return service.getPing();
    }

    @PostMapping("/add")
    public String add(@RequestBody Usage usage) {
        return service.add(usage);
    }

    @GetMapping("/get24h/{tid}/{did}/{mid}/{type}")
    public Long get24h(@PathVariable int tid, @PathVariable int did, @PathVariable int mid, @PathVariable String type) {
        return service.get24h(tid, did, mid, type);
    }

    @PostMapping("/addConfig")
    public String addLimitConfig(@RequestBody QuotaDto quotaDto) {
        return service.addLimitConfig(quotaDto);
    }

    @PostMapping("/checkAndUpdateUsage")
    public String checkAndUpdateUsage(@RequestBody QuotaDto quotaDto) {
        return service.checkAndUpdateUsage(quotaDto);
    }
}
