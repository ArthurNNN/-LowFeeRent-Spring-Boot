package com.lfr.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.lfr.app.boot.model.Person;

public interface PersonRepository extends CrudRepository<Person,String> {
	
}
