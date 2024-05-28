package com.priya.fms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long userId;
    private String pnr;
    private String ticketNo;
    private String paxName;
    private String gender;
    private Long age;
    private String flightNo;
    private String seatNo;
    private String bookingClass;
    private Date bookingDate;
    private Date flightDate;
    private String status;
    private Long fare;
}
