package com.selfhy.bus.payload.request;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class TripScheduleRequest {
	@ApiModelProperty(hidden = true)
	private Long id;
	
	private String tripDate;
	
	private int availableSeats; 
	
	private Long tripId;
	
	public TripScheduleRequest() {
	}

	public TripScheduleRequest(Long id,  @NotBlank String tripDate, @NotNull int availableSeats,
			@NotBlank Long tripId) {
		this.id = id;
		this.tripDate = tripDate;
		this.availableSeats = availableSeats;
		this.tripId = tripId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTripDate() {
		return tripDate;
	}

	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}
	
	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}


	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
}
