package com.poc.comm.controller;

import com.poc.comm.beans.Usage;
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

    @GetMapping("/get24h/{id}")
    public String get24h(@PathVariable int id) {
        return service.get24h(id);
    }
}
