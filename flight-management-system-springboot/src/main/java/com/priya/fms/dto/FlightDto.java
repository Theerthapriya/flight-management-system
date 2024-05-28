package com.priya.fms.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class FlightDto {
    private Long flightId;
    private String flightNo;
    private Date depDate;
    private String origin;
    private String destination;
    private String arrTime;
    private String depTime;
    private Long capacity;
    private String status;
    private Date arrDate;
    private String duration;
    private int ticketsBooked;
    private int available;
    private int totalAmount;
    
}
