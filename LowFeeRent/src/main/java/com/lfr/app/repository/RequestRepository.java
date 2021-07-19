package com.lfr.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.lfr.app.boot.model.Request;

public interface RequestRepository extends CrudRepository<Request, Integer> {
	
}
