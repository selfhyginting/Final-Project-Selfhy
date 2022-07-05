package com.selfhy.bus.payload.request;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class TicketRequest {
	
	@ApiModelProperty(hidden = true)
	private Long id;
	
	private int seatNumber;
	
	private Boolean cancellable;
	
	private String journeyDate;
	
	private Long passenger;
	
	private Long tripScheduleId;
	
	public TicketRequest() {
	}

	public TicketRequest(Long id, @NotNull int seatNumber, @NotNull Boolean cancellable, @NotBlank String journeyDate,
			 @NotBlank Long passenger, @NotBlank Long tripScheduleId) {
		this.id = id;
		this.seatNumber = seatNumber;
		this.cancellable = cancellable;
		this.journeyDate = journeyDate;
		this.passenger = passenger;
		this.tripScheduleId = tripScheduleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Boolean getCancellable() {
		return cancellable;
	}

	public void setCancellable(Boolean cancellable) {
		this.cancellable = cancellable;
	}

	public String getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(String journeyData) {
		this.journeyDate = journeyData;
	}

	public Long getPassenger() {
		return passenger;
	}

	public void setPassenger(Long passenger) {
		this.passenger = passenger;
	}

	public Long getTripScheduleId() {
		return tripScheduleId;
	}

	public void setTripScheduleId(Long tripScheduleId) {
		this.tripScheduleId = tripScheduleId;
	}
}
