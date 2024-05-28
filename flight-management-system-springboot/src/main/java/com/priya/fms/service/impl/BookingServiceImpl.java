package com.priya.fms.service.impl;


import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.BookingDto;
import com.priya.fms.dto.BookingPnrDto;
import com.priya.fms.dto.FlightDto;
import com.priya.fms.dto.PnrDto;
import com.priya.fms.entity.BookingEntity;
import com.priya.fms.repo.BookingRepository;
import com.priya.fms.service.BookingService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;
    

    @Override
    public APIResponse addBooking(List<BookingDto> bookingDtoList) {
        APIResponse apiResponse = new APIResponse();
        List<BookingEntity> bookings = bookingRepository.findAllByFlightDateAndFlightNoAndStatus(bookingDtoList.get(0).getFlightDate(),bookingDtoList.get(0).getFlightNo(),"Booked");
		if(bookings!=null && !bookings.isEmpty()) {	
			if(bookingDtoList.size() > (60-bookings.size())) {
				apiResponse.setError("Booking Failed ");
	            apiResponse.setStatus(500);            
	            return apiResponse;
			}
		}
        List<BookingEntity> bookingEntityList = new ArrayList<>();
        String pnr = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        for(BookingDto dto : bookingDtoList) {
            BookingEntity entity = new BookingEntity();
            entity.setPnr(pnr);
            entity.setUserId(dto.getUserId());
            entity.setPaxName(dto.getPaxName());
            entity.setGender(dto.getGender());
            entity.setAge(dto.getAge());
            entity.setFlightDate(dto.getFlightDate());
            entity.setFlightNo(dto.getFlightNo());
            entity.setBookingClass(dto.getBookingClass());
            entity.setSeatNo(dto.getSeatNo());
            entity.setStatus("Booked");
            entity.setFare(dto.getFare());
            entity.setBookingDate(new Date(System.currentTimeMillis()));
            bookingEntityList.add(entity);
        }
        bookingRepository.saveAll(bookingEntityList);
        apiResponse.setData("Booking Created Successfully");
        return apiResponse;
    }

    @Override
    public List<BookingPnrDto> getBookingsByFlight(FlightDto flightDto) throws ParseException {
        List<BookingPnrDto> bookings = new ArrayList<>();
        log.info("flightDto {}",flightDto);
        List<PnrDto> pnrData = bookingRepository.getBookingsByFlight(flightDto.getDepDate(),flightDto.getFlightNo());
        for(PnrDto entity : pnrData) {
        	BookingPnrDto booking = new BookingPnrDto();
            booking.setUserId(entity.getUserId());
            booking.setPnr(entity.getPnr());
            booking.setFlightDate(entity.getFlightDate().substring(0, 10));
            booking.setFlightNo(entity.getFlightNo());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fltDate = inputFormat.parse(entity.getFlightDate().substring(0, 10));
            if(entity.getStatus().equalsIgnoreCase("Booked") && fltDate.compareTo(new java.util.Date()) < 0) {
            	booking.setStatus("Travelled");
            }
            else {
            	booking.setStatus(entity.getStatus());
            }
            
            booking.setBookingDate(entity.getBookingDate().substring(0, 10));
            booking.setFare(entity.getFare());
            bookings.add(booking);
        }
        return bookings;
    }
    
    @Override
    public List<BookingPnrDto> getAllByPnr(Long userId) throws ParseException {
    	List<BookingPnrDto> bookings = new ArrayList<>();
        List<PnrDto> pnrData = bookingRepository.findAllByPnr(userId);
        for(PnrDto entity : pnrData) {
        	BookingPnrDto booking = new BookingPnrDto();
            booking.setUserId(entity.getUserId());
            booking.setPnr(entity.getPnr());
            booking.setFlightDate(entity.getFlightDate().substring(0, 10));
            booking.setFlightNo(entity.getFlightNo());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fltDate = inputFormat.parse(entity.getFlightDate().substring(0, 10));
            if(entity.getStatus().equalsIgnoreCase("Booked") && fltDate.compareTo(new java.util.Date()) < 0) {
            	booking.setStatus("Travelled");
            }
            else {
            	booking.setStatus(entity.getStatus());
            }
            booking.setBookingDate(entity.getBookingDate().substring(0, 10));
            booking.setFare(entity.getFare());
            bookings.add(booking);
        }
        return bookings;
    }
    
    @Override
    public List<BookingDto> getBookingsByPnr(String pnr) throws ParseException {
        List<BookingDto> bookings = new ArrayList<>();
        List<BookingEntity> bookingEntityList = bookingRepository.findAllByPnr(pnr);
        for(BookingEntity entity : bookingEntityList) {
            BookingDto booking = new BookingDto();
            booking.setUserId(entity.getUserId());
            booking.setPaxName(entity.getPaxName());
            booking.setPnr(entity.getPnr());
            booking.setTicketNo("Ticket-"+entity.getBookingId().toString());
            booking.setPaxName(entity.getPaxName());
            booking.setGender(entity.getGender());
            booking.setAge(entity.getAge());
            booking.setFlightDate(entity.getFlightDate());
            booking.setFlightNo(entity.getFlightNo());
            booking.setBookingClass(entity.getBookingClass());
            booking.setSeatNo(entity.getSeatNo());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fltDate = inputFormat.parse(entity.getFlightDate().toString().substring(0, 10));
            if(entity.getStatus().equalsIgnoreCase("Booked") && fltDate.compareTo(new java.util.Date()) < 0) {
            	booking.setStatus("Travelled");
            }
            else {
            	booking.setStatus(entity.getStatus());
            }
            booking.setBookingDate(entity.getBookingDate());
            booking.setFare(entity.getFare());
            bookings.add(booking);
        }
        return bookings;
    }
    
    @Override
    public  APIResponse cancelBooking(String pnr) {
    	 APIResponse apiResponse = new APIResponse();
    	 bookingRepository.cancelBooking(pnr);
         apiResponse.setData("Booking CAncelled Successfully");
         return apiResponse;
    }
    
    
}

