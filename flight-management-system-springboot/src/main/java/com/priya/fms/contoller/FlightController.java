package com.priya.fms.contoller;

import com.priya.fms.common.APIResponse;
import com.priya.fms.dto.FlightDto;
import com.priya.fms.service.impl.FlightServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FlightController {

	@Autowired
	public FlightServiceImpl flightService;

	@PostMapping(value = "/addFlight", consumes = "application/json", produces = "application/json")
	private ResponseEntity<APIResponse> addFlight(@RequestBody FlightDto flightDto) {
		APIResponse apiResponse =flightService.addFlight(flightDto);
		apiResponse.setData("flght Added Successfully");
		return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
		//return ResponseEntity.status(HttpStatus.OK).body(new APIResponse().setData("flght Added Successfully"));
	}

	@PostMapping(value = "/modifyFlight")
	private ResponseEntity<String> modifyFlight(@RequestBody FlightDto flightDto) {
		flightService.modifyFlight(flightDto);
		return ResponseEntity.status(HttpStatus.OK).body("Flight Modified Successfully");
	}

	@PostMapping(value = "/cancelFlight")
	private ResponseEntity<APIResponse> cancelFlight(@RequestBody FlightDto flightDto) {
		APIResponse apiResponse = new APIResponse();
		flightService.cancelFlight(flightDto);
		apiResponse.setData("Flight Cancelled Successfully");
		return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
	}

	@PostMapping(value = "/getFlights")
	private ResponseEntity<List<FlightDto>> getAllFlights(@RequestBody FlightDto flightDto) throws ParseException{
		List<FlightDto> list = flightService.getAllFlights(flightDto);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@GetMapping(value = "/searchFlights/{date}")
	private ResponseEntity<List<FlightDto>> getAllFlightsByDate(@RequestParam String date) {
		List<FlightDto> list = flightService.getAllFlightsByDate(date);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PostMapping(value = "/getSeats")
	private ResponseEntity<List<String>> getAllSeats(@RequestBody FlightDto flightDto) {
		List<String> list = flightService.getAllSeats(flightDto);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}
