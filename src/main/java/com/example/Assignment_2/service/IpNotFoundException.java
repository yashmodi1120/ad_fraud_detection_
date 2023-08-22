package com.example.Assignment_2.service;

public class IpNotFoundException extends RuntimeException {
    public IpNotFoundException(String ipAddress) {
        super(ipAddress);
    }
}
