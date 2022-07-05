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


import com.selfhy.bus.model.Ticket;
import com.selfhy.bus.model.TripSchedule;
import com.selfhy.bus.model.User;

import com.selfhy.bus.payload.request.TicketRequest;
import com.selfhy.bus.payload.response.MessageResponse;

import com.selfhy.bus.repository.TicketRepository;
import com.selfhy.bus.repository.TripScheduleRepository;
import com.selfhy.bus.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reservation/bookticket")
public class TicketController {
	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TripScheduleRepository tripScheduleRepository;

	@GetMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<TicketRequest> dataArrResult = new ArrayList<>();
		for (Ticket dataArr : ticketRepository.findAll()) {
			dataArrResult.add(new TicketRequest(dataArr.getId(), dataArr.getSeatNumber(), dataArr.getCancellable(),
					dataArr.getJourneyDate(), dataArr.getPassenger().getId(),dataArr.getTripSchedule().getId()));
		}
		return ResponseEntity.ok(new MessageResponse<TicketRequest>(true, "Success Retrieving Data", dataArrResult));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getTicketById(@PathVariable(value = "id") Long id) {
		Ticket ticket = ticketRepository.findById(id).get();
		if (ticket == null) {
			return ResponseEntity.notFound().build();
		} else {
			TicketRequest dataResult = new TicketRequest(ticket.getId(), ticket.getSeatNumber(), ticket.getCancellable(),
					ticket.getJourneyDate(), ticket.getPassenger().getId(), ticket.getTripSchedule().getId());
			return ResponseEntity.ok(new MessageResponse<TicketRequest>(true, "Success Retrieving Data", dataResult));
		}
	}

	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> addTicket(@Valid @RequestBody TicketRequest ticketRequest) {
		User user = userRepository.findById(ticketRequest.getPassenger()).get();
		TripSchedule tripSchedule= tripScheduleRepository.findById(ticketRequest.getTripScheduleId()).get();
		Ticket ticket = new Ticket(ticketRequest.getSeatNumber(), ticketRequest.getCancellable(), ticketRequest.getJourneyDate(), user, tripSchedule);
		
		ticketRepository.save(ticket);
		
		return ResponseEntity.ok(new MessageResponse("Success Adding Data"));
	
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTicket(@PathVariable(value = "id") Long id,
			@Valid @RequestBody TicketRequest ticketDetail) {
		Ticket ticket= ticketRepository.findById(id).get();
		User user = userRepository.findById(ticketDetail.getPassenger()).get();
		TripSchedule tripSchedule = tripScheduleRepository.findById(ticketDetail.getTripScheduleId()).get();
		
		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}
		ticket.setSeatNumber(ticketDetail.getSeatNumber());
		ticket.setCancellable(ticketDetail.getCancellable());
		ticket.setJourneyDate(ticketDetail.getJourneyDate());
		ticket.setPassenger(user);
		ticket.setTripSchedule(tripSchedule);

		ticketRepository.save(ticket);
		return ResponseEntity.ok(new MessageResponse("Success Update Data"));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTicket(@PathVariable(value = "id") Long id) {
		String result = "";
		try {
			ticketRepository.findById(id).get();

			result = "Success Deleting Data with Id: " + id;
			ticketRepository.deleteById(id);

			return ResponseEntity.ok(new MessageResponse<Ticket>(true, result));
		} catch (Exception e) {
			result = "Data with Id: " + id + " Not Found";
			return ResponseEntity.ok(new MessageResponse<Ticket>(false, result));
		}
	}
}
