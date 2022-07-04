package com.selfhy.bus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.selfhy.bus.model.Stop;
import com.selfhy.bus.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
	List<Trip> findAllBySourceStopAndDestStop(Stop sourceStop, Stop destStop);

	@Query(value = "SELECT DISTINCT * FROM trip WHERE source_stop_id = :sourceStop AND dest_stop_id = :destStop", nativeQuery = true)
	List<Trip> findTripsByStops(Long sourceStop, Long destStop);

}