package com.priya.fms.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class BookingPnrDto {
	private Long userId;
    private String pnr;
    private String flightNo;
    private String bookingDate;
    private String flightDate;
    private String status;
    private Long fare;
}
