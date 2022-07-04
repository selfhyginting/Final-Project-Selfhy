package com.selfhy.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfhy.bus.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
