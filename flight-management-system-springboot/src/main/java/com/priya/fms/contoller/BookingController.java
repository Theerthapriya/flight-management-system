package com.priya.fms.contoller;


import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.BookingDto;
import com.priya.fms.dto.BookingPnrDto;
import com.priya.fms.dto.FlightDto;
import com.priya.fms.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping(value = "/booking")
    private ResponseEntity<APIResponse> addBooking(@RequestBody List<BookingDto> bookingDtoList) {
         APIResponse apiResponse = bookingService.addBooking(bookingDtoList);
		apiResponse.setData("Booking Successful");
		return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping(value = "/viewBookings")
    private ResponseEntity<List<BookingPnrDto>> getBookings(@RequestBody FlightDto flightDto) throws ParseException {
    	List<BookingPnrDto> list = bookingService.getBookingsByFlight(flightDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }
    
    @GetMapping(value = "/getBookings/{userId}")
    private ResponseEntity<List<BookingPnrDto>> getAllByPnr(@PathVariable Long userId) throws ParseException {
        List<BookingPnrDto> list = bookingService.getAllByPnr(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }
    
    @GetMapping(value = "/getBookings/pnr/{pnr}")
    private ResponseEntity<List<BookingDto>> getAllByPnr(@PathVariable String pnr)throws ParseException {
        List<BookingDto> list = bookingService.getBookingsByPnr(pnr);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }
    
    @PutMapping(value = "/cancelBooking/pnr/{pnr}")
    private ResponseEntity<APIResponse> cancelBooking(@PathVariable String pnr) {
        APIResponse apiResponse = new APIResponse();
    	bookingService.cancelBooking(pnr);
		apiResponse.setData("Booking Cancelled Successfully");
		return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
