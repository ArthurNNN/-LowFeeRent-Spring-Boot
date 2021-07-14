package com.lfr.app.boot.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "apartment")
public class Apartment {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")

	@ElementCollection
	@Column(name = "start")
	@MapKeyColumn(name = "end")

	Map<LocalDate, LocalDate> openDates;
	
	@OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
	private List<Booking> bookings = new ArrayList<Booking>();

	@Column(name = "price")
	int price;
	@Column(name = "area")
	int area;
	@Column(name = "rooms")
	int rooms;
	@Column(name = "bathrooms")
	int bathrooms;
	String address;
	String lessorId;

	private static int counter;

	public static int getNumOfInstances() {
		return counter;
	}

	public Apartment() {
		super();
		// counter++;
		this.setId();
//		this.openDates = new ArrayList<LocalDate>();

	}

	public Apartment(int price, int area, int rooms, int bathrooms, String address) {
		super();
		counter++;
		this.setId();
		this.price = price;
		this.area = area;
		this.rooms = rooms;
		this.bathrooms = bathrooms;
		this.address = address;

	}

	public String getId() {
		return id;
	}

	public void setId() {
		String part = "";
		if (Apartment.getNumOfInstances() < 10) {
			part = "0";
		}
		this.id = "a" + part + Apartment.getNumOfInstances();

	}
	
	

	public List<Booking> getBookings() {
		return bookings;
	}

	public void addBooking(Booking booking) {
		this.bookings.add(booking);
		booking.setApartment(this);
	}

	public String getLessorId() {

		return lessorId;
	}

	public void setLessorId(String lessorId) {
		this.lessorId = lessorId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public int getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}

	public Map<LocalDate, LocalDate> getOpenDates() {
		return openDates;
	}

	public void setOpenDates(Map<LocalDate, LocalDate> openDates) {
		this.openDates = openDates;
	}

	@Override
	public String toString() {
		return "Apartment [id=" + id + ", lessorId=" + lessorId +
		 ", openDates=" + openDates +
				", price=" + price + ", area=" + area + ", rooms=" + rooms + ", bathrooms=" + bathrooms + ", address="
				+ address + "]";
	}
}