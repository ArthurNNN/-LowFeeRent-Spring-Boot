package com.lfr.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.lfr.app.boot.model.Booking;

public interface BookingRepository extends CrudRepository<Booking,String> {
	
}
