package com.priya.fms.service;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.BookingDto;
import com.priya.fms.dto.BookingPnrDto;
import com.priya.fms.dto.FlightDto;

import java.text.ParseException;
import java.util.List;

public interface BookingService {
    APIResponse addBooking(List<BookingDto> bookingDtoList);
    List<BookingPnrDto> getBookingsByFlight(FlightDto flightDto) throws ParseException;
    List<BookingPnrDto> getAllByPnr(Long UserId) throws ParseException;
    List<BookingDto> getBookingsByPnr(String pnr)throws ParseException;
    APIResponse cancelBooking(String pnr);
}
