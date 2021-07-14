package com.lfr.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.lfr.app.boot.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount,Integer> {
	
}
