package com.example.Assignment_2.service;

import com.example.Assignment_2.model.Ip;
import com.example.Assignment_2.model.IpLog;
import com.example.Assignment_2.repository.IpLogRepo;
import com.example.Assignment_2.repository.IpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@EnableCaching
@EnableScheduling
public class IpService {

    private static final String Cache_value = "ipRanges";
    private final IpRepo ipRepo;
    private final IpLogRepo ipLogRepo;
    @Autowired
    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    public IpService(IpRepo ipRepo, IpLogRepo ipLogRepo, RedisTemplate<String, String> redisTemplate) {
        this.ipRepo = ipRepo;
        this.ipLogRepo = ipLogRepo;
        this.redisTemplate = redisTemplate;
    }

    //updates cache from database after a fixed interval
    @Scheduled(fixedRate = 60000) // Run every 1 min
    @CacheEvict(value = "ipRanges", allEntries = true)
    public void updateCacheFromDatabase() {
        List<Ip> ipRanges = ipRepo.findAll();
        for (Ip ipRange : ipRanges) {
            System.out.println(ipRange.getIpAddress());
            String cacheValue = ipRange.getIpAddress() + "," + ipRange.getIpType() + "," + ipRange.getCountry();
            redisTemplate.opsForValue().set(ipRange.getIpAddress(), cacheValue);
        }
    }

    public Ip insertIp(Ip ip) {
        return ipRepo.save(ip);
    }
    public List<Ip> getAllIp() {
        return ipRepo.findAll();
    }
    @Cacheable(value = Cache_value, key = "#ipAddress")
    public Ip findIpByIpAddress(String ipAddress) {
        return ipRepo.findById(ipAddress).orElseThrow(() -> new IpNotFoundException(ipAddress));
    }

    @CacheEvict(value = Cache_value, key = "#ipAddress")
    public void deleteByIp(String ipAddress) {
        ipRepo.deleteById(ipAddress);
    }

    @Cacheable(value = Cache_value, key = "#ipAddress")
    public String getIpType(String ipAddress) {
        String cacheValue = redisTemplate.opsForValue().get(ipAddress);
        System.out.println(cacheValue);
        IpLog ipLog = new IpLog();
        if (cacheValue != null) {
            String[] values = cacheValue.split(",");
            //Logging values back to postgres
            ipLog.setIpLogAddress(values[0]);
            if(Objects.equals(values[1], "true"))
                ipLog.setIpLogType(true);
            else
                ipLog.setIpLogType(false);
            ipLog.setIpLogCountry(values[2]);
            ipLogRepo.save(ipLog);

            return values[1]; // Return IP type
        } else {
            System.out.println("got by database");
            Ip ip = ipRepo.findById(ipAddress).orElse(null);
            if(ip != null) {
                String cacheValue1 = ip.getIpAddress() + "," + ip.getIpType() + "," + ip.getCountry();
                redisTemplate.opsForValue().set(ipAddress, cacheValue1);
                String[] values1 = cacheValue1.split(",");
                //Logging values back to table
                ipLog.setIpLogAddress(values1[0]);
                if(Objects.equals(values1[1], "true"))
                    ipLog.setIpLogType(true);
                else
                    ipLog.setIpLogType(false);
                ipLog.setIpLogCountry(values1[2]);
                ipLogRepo.save(ipLog);
                return String.valueOf(ip.getIpType());
            }
            else {
                return null;
            }
        }
    }

}
