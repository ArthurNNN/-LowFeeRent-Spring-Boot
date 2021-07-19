package com.lfr.app.boot.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

import com.lfr.app.boot.utils.Utils;

@Entity
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

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

	}

	public Booking(Person person, Apartment apt, LocalDate checkin, LocalDate checkout, int amount) {
		super();

		this.checkin = checkin;
		this.checkout = checkout;
//		this.nights = Period.between(checkin, checkout).getDays();
		this.nights = (int) ChronoUnit.DAYS.between(checkin, checkout);
		this.amount = (int) nights * apt.getPrice() / 30;
		this.person = person;
		this.apartment = apt;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		person.addBooking(this);
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
		apartment.addBooking(this);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "Booking [id=" + id + ", checkin=" + checkin + ", checkout=" + checkout + ", nights=" + nights
				+ ", amount=" + amount + ", person=" + person + ", apartment=" + apartment + "]";
	}

}
