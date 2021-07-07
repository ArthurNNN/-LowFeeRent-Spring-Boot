package com.lfr.rental;

import java.time.LocalDate;
//import java.util.Date;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.lfr.utils.Utils;

@Entity
@Table
public class Booking {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String id;
	
	@ManyToOne
	@JoinColumn(name = "PERSON_FID")	
	Person person;
	
	@ManyToOne
	@JoinColumn(name = "APARTMENT_FID")
	Apartment apartment;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate checkin;
	LocalDate checkout;
	
	int nights;
	int amount;

	public Booking() {
		super();
		this.setId();
	}

	public Booking(Person person, Apartment apt, LocalDate checkin, LocalDate checkout, int amount) {
		super();
		this.setId();
//		this.personId = person.getId();
//		this.aptId = apt.getId();
		this.checkin = checkin;
		this.checkout = checkout;
		this.nights = Period.between(checkin, checkout).getDays();
		this.amount = amount;
	}
	
	

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId() {
		this.id = "b" + Utils.generateId();
	}

//	public String getPersonId() {
//		return personId;
//	}
//
//	public void setPersonId(String personId) {
//		this.personId = personId;
//	}
//
//	public String getAptId() {
//		return aptId;
//	}
//
//	public void setAptId(String aptId) {
//		this.aptId = aptId;
//	}

	public LocalDate getCheckin() {
		return checkin;
	}

	public void setCheckin(LocalDate checkin) {
		this.checkin = checkin;
	}

	public LocalDate getCheckout() {
		return checkout;
	}

	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}

}
