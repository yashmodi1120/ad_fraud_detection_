package com.example.Assignment_2.repository;

import com.example.Assignment_2.model.Ip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRepo extends JpaRepository<Ip, String> {

}
