package com.priya.fms.service;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.FlightDto;

import java.text.ParseException;
import java.util.List;

public interface FlightService {

    APIResponse addFlight(FlightDto flightDto);
    APIResponse modifyFlight(FlightDto flightDto);
    APIResponse cancelFlight(FlightDto flightDto);
    List<FlightDto> getAllFlights(FlightDto flightDto) throws ParseException;

    List<FlightDto> getAllFlightsByDate(String Date);
    
    List<String> getAllSeats(FlightDto flightDto);

}
