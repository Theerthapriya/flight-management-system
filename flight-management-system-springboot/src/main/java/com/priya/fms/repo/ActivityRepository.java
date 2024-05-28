package com.priya.fms.repo;

import com.priya.fms.entity.ActivityLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityLogsEntity, Long> {
    List<ActivityLogsEntity> findAllByUserName(String userName);

}
