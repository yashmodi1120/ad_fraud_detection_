package com.example.Assignment_2.controller;

import com.example.Assignment_2.model.Ip;
import com.example.Assignment_2.service.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Ip")
public class  IpController {

    private final IpService ipService;

    @Autowired
    public IpController(IpService ipService) {
        this.ipService = ipService;
    }

    private final Logger logger = LoggerFactory.getLogger(IpController.class);
    @GetMapping
    public ResponseEntity<List<Ip>> getAllIp() {
        List<Ip> ip = ipService.getAllIp();
        logger.info(">> IpController : /Ip : ");
        return new ResponseEntity<>(ip, HttpStatus.OK);
    }

    @GetMapping("/find/{ipAddress}")
    public ResponseEntity<String> getIpType(@PathVariable("ipAddress") String ipAddress) {
        String ipType = ipService.getIpType(ipAddress);
        if (ipType != null) {
            return ResponseEntity.ok("IP is valid: " + ipType);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid IP");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Ip> insertIp(@RequestBody Ip ip) {
        Ip newip = ipService.insertIp(ip);
        logger.info(">> IpController : /Ip/add call: ");
        return new ResponseEntity<>(newip, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{ipAddress}")
    public ResponseEntity<?> delete(@PathVariable("ipAddress") String ipAddress) {
        ipService.deleteByIp(ipAddress);
        logger.info(">> IpController : /Ip/delete call : ");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/fullinfo/{ipAddress}")
    public ResponseEntity<Ip> findIpByIpAddress(@PathVariable("ipAddress") String ipAddress) {
        Ip ip = ipService.findIpByIpAddress(ipAddress);
        logger.info(">> IpController : /Ip/fullinfo call : ");
        return new ResponseEntity<>(ip, HttpStatus.OK);
    }
}


