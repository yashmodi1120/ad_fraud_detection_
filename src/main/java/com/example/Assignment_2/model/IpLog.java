package com.example.Assignment_2.model;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="LogTable")
public class IpLog implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ipAddress")
    private String ipLogAddress;

    @Column(name = "ipType")
    private boolean ipLogType;

    @Column(name = "ipCountry")
    private String ipLogCountry;

    public IpLog() {
    }
    public IpLog(Long id, String ipLogAddress, boolean ipLogType, String ipLogCountry) {
        this.id = id;
        this.ipLogAddress = ipLogAddress;
        this.ipLogType = ipLogType;
        this.ipLogCountry = ipLogCountry;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getIpLogAddress() {
        return ipLogAddress;
    }

    public boolean isIpLogType() {
        return ipLogType;
    }

    public String getIpLogCountry() {
        return ipLogCountry;
    }

    public void setIpLogAddress(String ipLogAddress) {
        this.ipLogAddress = ipLogAddress;
    }

    public void setIpLogType(boolean ipLogType) {
        this.ipLogType = ipLogType;
    }

    public void setIpLogCountry(String ipLogCountry) {
        this.ipLogCountry = ipLogCountry;
    }
}

