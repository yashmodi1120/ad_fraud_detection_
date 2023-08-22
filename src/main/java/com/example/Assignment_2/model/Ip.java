package com.example.Assignment_2.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "ipTable")
public class Ip implements Serializable {
    @Id
    @Column(name = "ipAddress")
    private String ipAddress;
    @Column(name = "ipType")
    private boolean ipType;
    @Column(name = "country")
    private String country;

    public Ip() {
    }

    public Ip(String ipAddress, boolean ipType, String country) {
        this.ipAddress = ipAddress;
        this.ipType = ipType;
        this.country = country;
    }
    public String getIpAddress() {
        return ipAddress;
    }


//    @Override
//    public java.lang.String toString() {
//        return ipAddress;
//    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean getIpType() {
        return ipType;
    }

    public void setIpType(boolean ipType) {
        this.ipType = ipType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
