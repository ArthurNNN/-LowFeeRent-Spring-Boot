package com.lfr.app.boot.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lfr.app.boot.model.Apartment;
import com.lfr.app.boot.model.Booking;
import com.lfr.app.boot.model.Person;
import com.lfr.app.boot.utils.Utils;
import com.lfr.app.repository.ApartmentRepository;
import com.lfr.app.repository.BookingRepository;
import com.lfr.app.repository.PersonRepository;

@Controller
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	ApartmentRepository apartmentRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	BookingRepository bookingRepository;


	@RequestMapping("/allBookings")
	public String getAllBookings(Model boxToView) {

		boxToView.addAttribute("bookingListfromControllerAndDB", bookingRepository.findAll());

		return "booking.html";
	}
	
	@RequestMapping("/fillinlessors")
	public String fillInLessors(Model boxToView) {
		
		System.out.print("\n---------------- Fill In Lessors to Apartments: ----------------");
		int n = 1;

		while (n <= 10) {

			int personIdRandom = Utils.randRange(1, (int) personRepository.count());
			int apartmentIdRandom = Utils.randRange(1, (int) apartmentRepository.count());
			
			Optional<Person> personRandom = personRepository
					.findById(personIdRandom);
			Optional<Apartment> apartmentRandom = apartmentRepository
					.findById(apartmentIdRandom);		

			if (apartmentRandom.isPresent() && personRandom.isPresent()) {
				Map<LocalDate, LocalDate> openDates = apartmentRandom.get().getOpenDates();
				Map.Entry<LocalDate, LocalDate> firstDateHM = openDates.entrySet().stream().findAny().get();
				LocalDate chekingDate = firstDateHM.getKey();
				LocalDate chekoutDate = firstDateHM.getValue();
				
				openDates.remove(chekingDate);

				Booking booking = new Booking( personRandom.get(), apartmentRandom.get(),
						chekingDate, chekoutDate, Utils.randRange(8, 10) * 10);
				
				booking = bookingRepository.save(booking);
				System.out.print("\n#" + n + " ");
				System.out.print(booking);
				n++;
			}
		}

		boxToView.addAttribute("apartmentList", apartmentRepository.findAll());

		return "redirect:/apartment/allApartments";
	}



	@RequestMapping("/fillInBookings")
	public String fillInBookings(Model boxToView) {
		System.out.print("\n---------------- Adding 10 bookings: ----------------");
		int n = 1;

		while (n <= 3) {

			int personIdRandom = Utils.randRange(1, (int) personRepository.count());
			int apartmentIdRandom = Utils.randRange(1, (int) apartmentRepository.count());
			
			Optional<Person> personRandom = personRepository
					.findById(personIdRandom);
			Optional<Apartment> apartmentRandom = apartmentRepository
					.findById(apartmentIdRandom);		

			if (apartmentRandom.isPresent() && personRandom.isPresent()) {
				Map<LocalDate, LocalDate> openDates = apartmentRandom.get().getOpenDates();
				Map.Entry<LocalDate, LocalDate> firstDateHM = openDates.entrySet().stream().findAny().get();
				LocalDate chekingDate = firstDateHM.getKey();
				LocalDate chekoutDate = firstDateHM.getValue();
				
				openDates.remove(chekingDate);

				Booking booking = new Booking( personRandom.get(), apartmentRandom.get(),
						chekingDate, chekoutDate, Utils.randRange(8, 10) * 10);
				
				booking = bookingRepository.save(booking);
				System.out.print("\n#" + n + " ");
				System.out.print(booking);
				n++;
			}
		}

		boxToView.addAttribute("bookingListfromControllerAndDB", bookingRepository.findAll());

		return "redirect:/booking/allBookings";
	}
	
	// -----------------------detail----------------------------------
	@RequestMapping("/detailBooking")
	public String detailBooking(int id, Model model) {

		Optional<Booking> bookingFound = bookingRepository.findById(id);

		if (bookingFound.isPresent()) {

			model.addAttribute("bookingfromController", bookingFound.get());
			return "detailbooking";
		}

		else
			return "notfound";
	}

	// -----------------------delete----------------------------------
	@RequestMapping("/deleteBooking")
	public String removeBooking(int id, Model model) {

		 System.out.println("inside removeBooking" + id);
		Optional<Booking> bookingFound = bookingRepository.findById(id);

		 System.out.println("find inside removeBooking" + bookingFound.get());

		if (bookingFound.isPresent()) {

			bookingRepository.deleteById(id);
			model.addAttribute("message", "done");
			model.addAttribute("bookingDeleted", bookingFound.get());
		}

		else {
			model.addAttribute("message", "error");
		}

		 System.out.println("finishing removeBooking" + id);
		return "deletedbooking";
	}

	@RequestMapping("/deleteAllBookings")
	public String deleteAllBookings() {

		bookingRepository.deleteAll();

		return "redirect:/booking/allBookings";

	}

}
