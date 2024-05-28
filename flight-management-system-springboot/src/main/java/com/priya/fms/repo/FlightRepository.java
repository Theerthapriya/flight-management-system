package com.priya.fms.repo;

import com.priya.fms.dto.PnrDto;
import com.priya.fms.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    List<FlightEntity>  findAllByDepDate(String Date);
    
    List<FlightEntity>  findAllByOriginAndDestinationAndDepDateAndStatus(String origin, String destintion, Date Date,String status);
    
    List<FlightEntity>  findAllByDepDateAndFlightNo(Date Date,String flightNo);

}
