package com.priya.fms.repo;

import com.priya.fms.entity.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailAndPassword(String emailId, String password);
    List<UserEntity> findAllByEmail(String email);
}
