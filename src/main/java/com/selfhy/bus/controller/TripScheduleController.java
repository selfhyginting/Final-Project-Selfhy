package com.selfhy.bus.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfhy.bus.model.Trip;
import com.selfhy.bus.model.TripSchedule;
import com.selfhy.bus.payload.request.TripScheduleRequest;
import com.selfhy.bus.payload.response.MessageResponse;

import com.selfhy.bus.repository.TripRepository;
import com.selfhy.bus.repository.TripScheduleRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reservation/tripschedules")
public class TripScheduleController {

	@Autowired
	TripRepository tripRepository;

	@Autowired
	TripScheduleRepository tripScheduleRepository;


	@GetMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<TripScheduleRequest> dataArrResult = new ArrayList<>();
		for (TripSchedule dataArr : tripScheduleRepository.findAll()) {
			dataArrResult.add(new TripScheduleRequest(dataArr.getId(), dataArr.getTripDate(), dataArr.getAvailableSeats(),
					dataArr.getTripDetail().getId()));
		}
		return ResponseEntity.ok(new MessageResponse<TripScheduleRequest>(true, "Success Retrieving Data", dataArrResult));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getTripScheduleById(@PathVariable(value = "id") Long id) {
		TripSchedule tripSchedule = tripScheduleRepository.findById(id).get();
		if (tripSchedule == null) {
			return ResponseEntity.notFound().build();
		} else {
			TripScheduleRequest dataResult = new TripScheduleRequest(tripSchedule.getId(), tripSchedule.getTripDate(), tripSchedule.getAvailableSeats(),
					tripSchedule.getTripDetail().getId());
			return ResponseEntity.ok(new MessageResponse<TripScheduleRequest>(true, "Success Retrieving Data", dataResult));
		}
	}

	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTripSchedule(@Valid @RequestBody TripScheduleRequest tripScheduleRequest) {
		Trip trip = tripRepository.findById(tripScheduleRequest.getTripId()).get();
		TripSchedule tripSchedule = new TripSchedule(tripScheduleRequest.getTripDate(), tripScheduleRequest.getAvailableSeats(), trip);
		
		tripScheduleRepository.save(tripSchedule);
		
		return ResponseEntity.ok(new MessageResponse("Success Adding Data"));
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTripSchedule(@PathVariable(value = "id") Long id,
			@Valid @RequestBody TripScheduleRequest tripScheduleDetail) {
		TripSchedule tripSchedule= tripScheduleRepository.findById(id).get();
		Trip trip= tripRepository.findById(tripScheduleDetail.getTripId()).get();
		if (tripSchedule == null) {
			return ResponseEntity.notFound().build();
		}
		tripSchedule.setAvailableSeats(tripScheduleDetail.getAvailableSeats());
		tripSchedule.setTripDate(tripScheduleDetail.getTripDate());
		tripSchedule.setTripDetail(trip);

		tripScheduleRepository.save(tripSchedule);
		return ResponseEntity.ok(new MessageResponse("Success Update Data"));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTripSchedule(@PathVariable(value = "id") Long id) {
		String result = "";
		try {
			tripScheduleRepository.findById(id).get();

			result = "Success Deleting Data with Id: " + id;
			tripScheduleRepository.deleteById(id);

			return ResponseEntity.ok(new MessageResponse<TripSchedule>(true, result));
		} catch (Exception e) {
			result = "Data with Id: " + id + " Not Found";
			return ResponseEntity.ok(new MessageResponse<TripSchedule>(false, result));
		}
	}
}
