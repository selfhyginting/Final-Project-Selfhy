package com.selfhy.bus.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

@Entity
@Table(name = "trip_schedule")
public class TripSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tripDate;
	
	private int availableSeats;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_id")
	private Trip tripDetail;
	
	@OneToMany(
			mappedBy = "tripSchedule", 
			cascade = CascadeType.ALL)
	private Set<Ticket> ticketsSold;
	


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

	
	public Trip getTripDetail() {
		return tripDetail;
	}

	public void setTripDetail(Trip tripDetail) {
		this.tripDetail = tripDetail;
	}

	public Set<Ticket> getTicketsSold() {
		return ticketsSold;
	}

	public void setTicketsSold(Set<Ticket> ticketsSold) {
		this.ticketsSold = ticketsSold;
	}

	public TripSchedule() {
    }
	
	public TripSchedule( @NotBlank String tripDate, @NotNull int availableSeats, Trip tripDetail) {
		this.tripDate = tripDate;
		this.availableSeats = availableSeats;
        this.tripDetail = tripDetail;
       // this.ticketsSold =ticketsSold;
    }
}
