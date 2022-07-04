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

import com.selfhy.bus.model.Agency;
import com.selfhy.bus.model.Bus;
import com.selfhy.bus.model.Stop;
import com.selfhy.bus.model.Trip;
import com.selfhy.bus.payload.request.GetTripByStopRequest;
import com.selfhy.bus.payload.request.TripRequest;
import com.selfhy.bus.payload.response.MessageResponse;
import com.selfhy.bus.repository.AgencyRepository;
import com.selfhy.bus.repository.BusRepository;
import com.selfhy.bus.repository.StopRepository;
import com.selfhy.bus.repository.TripRepository;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reservation")
public class TripController {


	@Autowired
	TripRepository tripRepository;
	
	@Autowired
	StopRepository stopRepository;
	
	@Autowired
	BusRepository busRepository;
	
	@Autowired
	AgencyRepository agencyRepository;


	@GetMapping("/trip")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<TripRequest> dataArrResult = new ArrayList<>();
		for (Trip dataArr : tripRepository.findAll()) {
			dataArrResult.add(new TripRequest(dataArr.getId(), dataArr.getFare(), dataArr.getJourneyTime(),
					dataArr.getSourceStop().getId(), dataArr.getDestStop().getId(),dataArr.getBus().getId(),dataArr.getAgency().getId() ));
		}
		return ResponseEntity.ok(new MessageResponse<TripRequest>(true, "Success Retrieving Data", dataArrResult));
	}

	@GetMapping("/trip/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getTripById(@PathVariable(value = "id") Long id) {
		Trip trip = tripRepository.findById(id).get();
		if (trip == null) {
			return ResponseEntity.notFound().build();
		} else {
			TripRequest dataResult = new TripRequest(trip.getId(), trip.getFare(), trip.getJourneyTime(),
					trip.getSourceStop().getId(), trip.getDestStop().getId(), trip.getBus().getId(), trip.getAgency().getId());
			return ResponseEntity.ok(new MessageResponse<TripRequest>(true, "Success Retrieving Data", dataResult));
		}
	}

	@PostMapping("/tripsbystops")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getTripsByStops(@Valid @RequestBody GetTripByStopRequest getTripByStopRequest) {
		List<TripRequest> dataArrResult = new ArrayList<>();
		Stop sourceStop= stopRepository.findById(getTripByStopRequest.getSourceStopid()).get();
		Stop destStop= stopRepository.findById(getTripByStopRequest.getDestStopId()).get();
		for (Trip dataArr : tripRepository.findAllBySourceStopAndDestStop(sourceStop,destStop)) {
			dataArrResult.add(new TripRequest(dataArr.getId(), dataArr.getFare(), dataArr.getJourneyTime(),
					dataArr.getSourceStop().getId(), dataArr.getDestStop().getId(),dataArr.getBus().getId(),dataArr.getAgency().getId() ));
		}
		return ResponseEntity.ok(new MessageResponse<TripRequest>(true, "Success Retrieving Data", dataArrResult));
	}
	
	@PostMapping("/trip")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTrip(@Valid @RequestBody TripRequest tripRequest) {
		Stop sourceStop= stopRepository.findById(tripRequest.getSourceStopId()).get();
		Stop destStop= stopRepository.findById(tripRequest.getDestStopId()).get();
		Bus bus = busRepository.findById(tripRequest.getBusId()).get();
		Agency agency = agencyRepository.findById(tripRequest.getAgencyId()).get();
		Trip trip = new Trip(tripRequest.getFare(), tripRequest.getJourneyTime(), sourceStop, destStop, bus,agency);
		return ResponseEntity
				.ok(new MessageResponse<Trip>(true, "Success Adding Data", tripRepository.save(trip)));
	}

	@PutMapping("/trip/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTrip(@PathVariable(value = "id") Long id,
			@Valid @RequestBody TripRequest tripDetail) {
		Trip trip = tripRepository.findById(id).get();
		Stop sourceStop= stopRepository.findById(tripDetail.getSourceStopId()).get();
		Stop destStop= stopRepository.findById(tripDetail.getDestStopId()).get();
		Bus bus = busRepository.findById(tripDetail.getBusId()).get();
		Agency agency = agencyRepository.findById(tripDetail.getAgencyId()).get();
		if (trip == null) {
			return ResponseEntity.notFound().build();
		}
		trip.setFare(tripDetail.getFare());
		trip.setJourneyTime(tripDetail.getJourneyTime());
		trip.setSourceStop(sourceStop);
		trip.setDestStop(destStop);
		trip.setBus(bus);
		trip.setAgency(agency);

		Trip updatedTrip= tripRepository.save(trip);

		return ResponseEntity.ok(new MessageResponse<Trip>(true, "Success Updating Data", updatedTrip));
	}

	@DeleteMapping("/trip/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTrip(@PathVariable(value = "id") Long id) {
		String result = "";
		try {
			tripRepository.findById(id).get();

			result = "Success Deleting Data with Id: " + id;
			tripRepository.deleteById(id);

			return ResponseEntity.ok(new MessageResponse<Trip>(true, result));
		} catch (Exception e) {
			result = "Data with Id: " + id + " Not Found";
			return ResponseEntity.ok(new MessageResponse<Trip>(false, result));
		}
	}
}
