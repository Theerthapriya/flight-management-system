package com.priya.fms.service.impl;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.FlightDto;
import com.priya.fms.entity.BookingEntity;
import com.priya.fms.entity.FlightEntity;
import com.priya.fms.entity.UserEntity;
import com.priya.fms.repo.BookingRepository;
import com.priya.fms.repo.FlightRepository;
import com.priya.fms.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	BookingRepository bookingRepository;

	@Override
	public APIResponse addFlight(FlightDto flightDto) {
		APIResponse apiResponse = new APIResponse();
		List<FlightEntity> flight = flightRepository.findAllByDepDateAndFlightNo(flightDto.getDepDate(),flightDto.getFlightNo());
        if(flight != null && !flight.isEmpty()){
            apiResponse.setError("FlightNumber Exits for the Date ");
            apiResponse.setStatus(500);            
            return apiResponse;
        }
		try {
			if (flightDto != null) {
				FlightEntity entity = new FlightEntity();
				entity.setFlightNo(flightDto.getFlightNo());
				entity.setDepDate(flightDto.getDepDate());
				entity.setArrDate(flightDto.getArrDate());
				entity.setArrTime(flightDto.getArrTime());
				entity.setDepTime(flightDto.getDepTime());
				entity.setStatus("Scheduled");
				entity.setCapacity(60L);
				entity.setOrigin(flightDto.getOrigin());
				entity.setDestination(flightDto.getDestination());
				entity.setDuration(flightDto.getDuration());
				flightRepository.save(entity);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		apiResponse.setData("Flight Added Successfully");
		return apiResponse;
	}

	@Override
	public APIResponse modifyFlight(FlightDto flightDto) {
		APIResponse apiResponse = new APIResponse();
		if (flightDto != null) {
			FlightEntity entity = new FlightEntity();
			entity.setId(flightDto.getFlightId());
			entity.setFlightNo(flightDto.getFlightNo());
			entity.setDepDate(flightDto.getDepDate());
			entity.setArrDate(flightDto.getArrDate());
			entity.setArrTime(flightDto.getArrTime());
			entity.setDepTime(flightDto.getDepTime());
			entity.setStatus("Scheduled");
			entity.setCapacity(60L);
			entity.setOrigin(flightDto.getOrigin());
			entity.setDestination(flightDto.getDestination());
			entity.setDuration(flightDto.getDuration());
			flightRepository.save(entity);
		}
		apiResponse.setData("Flight Modified Successfully");
		return apiResponse;
	}

	@Override
	public APIResponse cancelFlight(FlightDto flightDto) {
		APIResponse apiResponse = new APIResponse();
		if (flightDto != null) {
			FlightEntity entity = new FlightEntity();
			entity.setId(flightDto.getFlightId());
			entity.setFlightNo(flightDto.getFlightNo());
			entity.setDepDate(flightDto.getDepDate());
			entity.setArrDate(flightDto.getArrDate());
			entity.setArrTime(flightDto.getArrTime());
			entity.setDepTime(flightDto.getDepTime());
			entity.setStatus("Cancelled");
			entity.setCapacity(60L);
			entity.setOrigin(flightDto.getOrigin());
			entity.setDestination(flightDto.getDestination());
			entity.setDuration(flightDto.getDuration());
			flightRepository.save(entity);
			bookingRepository.cancelBookingByFlight(flightDto.getDepDate(), flightDto.getFlightNo());
		}
		apiResponse.setData("Flight Cancelled Successfully");
		return apiResponse;
	}

	@Override
	public List<FlightDto> getAllFlights(FlightDto flightDto) throws ParseException {
		List<FlightDto> flightDtoList = new ArrayList<>();
		if (flightDto.getOrigin() != null) {
			List<FlightEntity> flightEntityList = flightRepository.findAllByOriginAndDestinationAndDepDateAndStatus(
					flightDto.getOrigin(), flightDto.getDestination(), flightDto.getDepDate(),"Scheduled");
			for (FlightEntity entity : flightEntityList) {
				FlightDto flightDto1 = new FlightDto();
				List<BookingEntity> bookings = bookingRepository.findAllByFlightDateAndFlightNoAndStatus(entity.getDepDate(),entity.getFlightNo(),"Booked");
				flightDto1.setAvailable(60);
				flightDto1.setTotalAmount(0);
				if(bookings!=null && !bookings.isEmpty()) {
					flightDto1.setTicketsBooked(bookings.size());
					flightDto1.setAvailable(60-bookings.size());
					int total =0;
					for(BookingEntity booking : bookings) {
						total += booking.getFare();
					}
					flightDto1.setTotalAmount(total);
				}
				flightDto1.setFlightId(entity.getId());
				flightDto1.setFlightNo(entity.getFlightNo());
				flightDto1.setDepDate(entity.getDepDate());
				flightDto1.setArrDate(entity.getArrDate());
				flightDto1.setArrTime(entity.getArrTime());
				flightDto1.setDepTime(entity.getDepTime());
				flightDto1.setStatus(entity.getStatus());
				flightDto1.setOrigin(entity.getOrigin());
				flightDto1.setDestination(entity.getDestination());
				flightDto1.setDuration(entity.getDuration());
				flightDtoList.add(flightDto1);
			}
		} else {
			List<FlightEntity> flightEntityList = flightRepository.findAll();
			for (FlightEntity entity : flightEntityList) {
				FlightDto flightDto1 = new FlightDto();
				List<BookingEntity> bookings = bookingRepository.findAllByFlightDateAndFlightNoAndStatus(entity.getDepDate(),entity.getFlightNo(),"Booked");
				flightDto1.setAvailable(60);
				flightDto1.setTotalAmount(0);
				if(bookings!=null && !bookings.isEmpty()) {
					flightDto1.setTicketsBooked(bookings.size());
					flightDto1.setAvailable(60-bookings.size());
					int total =0;
					for(BookingEntity booking : bookings) {
						total += booking.getFare()!=null?booking.getFare():0;
					}
					flightDto1.setTotalAmount(total);
				}
				flightDto1.setFlightId(entity.getId());
				flightDto1.setFlightNo(entity.getFlightNo());
				flightDto1.setDepDate(entity.getDepDate());
				flightDto1.setArrDate(entity.getArrDate());
				flightDto1.setArrTime(entity.getArrTime());
				flightDto1.setDepTime(entity.getDepTime());
				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date fltDate = inputFormat.parse(entity.getDepDate().toString().substring(0, 10));
	            if(entity.getStatus().equalsIgnoreCase("Scheduled") && fltDate.compareTo(new java.util.Date()) < 0) {
	            	flightDto1.setStatus("Completed");
	            }
	            else {
	            	flightDto1.setStatus(entity.getStatus());
	            }
				flightDto1.setOrigin(entity.getOrigin());
				flightDto1.setDestination(entity.getDestination());
				flightDto1.setDuration(entity.getDuration());
				flightDtoList.add(flightDto1);
			}
		}
		return flightDtoList;
	}

	@Override
	public List<FlightDto> getAllFlightsByDate(String Date) {
		List<FlightDto> flightDtoList = new ArrayList<>();
		List<FlightEntity> flightEntityList = flightRepository.findAllByDepDate(Date);
		for (FlightEntity entity : flightEntityList) {
			FlightDto flightDto = new FlightDto();
			flightDto.setFlightId(entity.getId());
			flightDto.setFlightNo(entity.getFlightNo());
			flightDto.setDepDate(entity.getDepDate());
			flightDto.setArrDate(entity.getArrDate());
			flightDto.setArrTime(entity.getArrTime());
			flightDto.setDepTime(entity.getDepTime());
			flightDto.setStatus(entity.getStatus());
			flightDto.setOrigin(entity.getOrigin());
			flightDto.setDestination(entity.getDestination());
			flightDto.setDuration(entity.getDuration());
			flightDtoList.add(flightDto);
		}
		return flightDtoList;
	}
	
	public List<String> getAllSeats(FlightDto flightDto){
		return bookingRepository.findAllByFlightNumAndFlightDate(flightDto.getFlightNo(), flightDto.getDepDate());
	}
}
