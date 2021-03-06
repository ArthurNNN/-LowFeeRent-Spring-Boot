package com.lfr.app.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lfr.app.boot.utils.*;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String imgUrl;
	

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	private List<Booking> bookings = new ArrayList<Booking>();

//	private BankAccount bankAccount;

	public Person() {
//		super();
	}

	public Person(String name, String surname, String email
	// , BankAccount bankAccount
	) {
//		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
//		this.bankAccount = bankAccount;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void addBooking(Booking booking) {
		this.bookings.add(booking);
		booking.setPerson(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public BankAccount getBankAccount() {
//		return bankAccount;
//	}
//
//	public void setBankAccount(BankAccount bankAccount) {
//		this.bankAccount = bankAccount;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email +
		// ", bankAccount=" + bankAccount +
				"]";
	}

}
