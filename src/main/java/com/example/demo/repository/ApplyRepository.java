package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Apply;
import com.example.demo.entity.MyUser;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    public List<Apply> getApplysByUser(MyUser user);
}
