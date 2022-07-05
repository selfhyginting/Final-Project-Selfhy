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


import com.selfhy.bus.model.Stop;
import com.selfhy.bus.payload.request.StopRequest;
import com.selfhy.bus.payload.response.MessageResponse;

import com.selfhy.bus.repository.StopRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reservation/stops")
public class StopController {
	
	@Autowired
	StopRepository stopRepository;

	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<StopRequest> dataArrResult = new ArrayList<>();
		for (Stop dataArr : stopRepository.findAll()) {
			dataArrResult.add(new StopRequest(dataArr.getId(), dataArr.getCode(), dataArr.getName(),
					dataArr.getDetail()));
		}
		return ResponseEntity.ok(new MessageResponse<StopRequest>(true, "Success Retrieving Data", dataArrResult));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getStopById(@PathVariable(value = "id") Long id) {
		Stop stop= stopRepository.findById(id).get();
		if (stop == null) {
			return ResponseEntity.notFound().build();
		} else {
			StopRequest dataResult = new StopRequest(stop.getId(), stop.getCode(), stop.getName(),
					stop.getDetail());
			return ResponseEntity.ok(new MessageResponse<StopRequest>(true, "Success Retrieving Data", dataResult));
		}
	}
	
	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addStop(@Valid @RequestBody StopRequest stopRequest) {
		Stop stop = new Stop(stopRequest.getCode(), stopRequest.getName(), stopRequest.getDetail());
		
		stopRepository.save(stop);
		
		return ResponseEntity.ok(new MessageResponse("Success Adding Data"));
	
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStop(@PathVariable(value = "id") Long id,
			@Valid @RequestBody StopRequest stopDetail) {
		Stop stop = stopRepository.findById(id).get();
		if (stop == null) {
			return ResponseEntity.notFound().build();
		}
		stop.setCode(stopDetail.getCode());
		stop.setName(stopDetail.getName());
		stop.setDetail(stopDetail.getDetail());

		stopRepository.save(stop);
		return ResponseEntity.ok(new MessageResponse("Success Update Data"));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteStop(@PathVariable(value = "id") Long id) {
		String result = "";
		try {
			stopRepository.findById(id).get();

			result = "Success Deleting Data with Id: " + id;
			stopRepository.deleteById(id);

			return ResponseEntity.ok(new MessageResponse<Stop>(true, result));
		} catch (Exception e) {
			result = "Data with Id: " + id + " Not Found";
			return ResponseEntity.ok(new MessageResponse<Stop>(false, result));
		}
	}
}
