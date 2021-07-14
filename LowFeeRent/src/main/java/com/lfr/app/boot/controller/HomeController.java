package com.lfr.app.boot.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;
import com.lfr.app.boot.model.Apartment;
import com.lfr.app.boot.model.Request;
import com.lfr.app.boot.utils.Utils;
import com.lfr.app.repository.ApartmentRepository;
import com.lfr.app.repository.RequestRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	boolean isFirstRender = true;

	@Autowired
	ApartmentRepository apartmentRepository;

	@Autowired
	RequestRepository requestRepository;

	@RequestMapping("/")
	public String home(Model boxToView, Request req) {

		if (isFirstRender) {
			Faker faker = new Faker();
			int n = 1;
			while (n <= 32) {
				Apartment apartment = new Apartment(Utils.randRange(8, 25) * 100, Utils.randRange(6, 18) * 10,
						Utils.randRange(1, 5), Utils.randRange(1, 3), faker.address().streetAddress(true));

				Map<LocalDate, LocalDate> datesMap = new HashMap<LocalDate, LocalDate>();
				datesMap.put(LocalDate.of(2021, Utils.randRange(3, 5), Utils.randRange(1, 28)),
						LocalDate.of(2021, Utils.randRange(1, 3), Utils.randRange(1, 28)));
				datesMap.put(LocalDate.of(2021, Utils.randRange(6, 9), Utils.randRange(1, 28)),
						LocalDate.of(2021, Utils.randRange(5, 6), Utils.randRange(1, 28)));
				datesMap.put(LocalDate.of(2021, Utils.randRange(10, 13), Utils.randRange(1, 28)),
						LocalDate.of(2021, Utils.randRange(9, 10), Utils.randRange(1, 28)));

				System.out.println(datesMap);
				apartment.setOpenDates(datesMap);
				apartmentRepository.save(apartment);
				n++;
			}
			isFirstRender = false;
		}

		LocalDate checkin = req.getCheckin() != null ? req.getCheckin() : LocalDate.now();
		LocalDate checkout = req.getCheckout() != null ? req.getCheckout() : LocalDate.now();
		Integer price = req.getPriceMax() != null ? req.getPriceMax() : 5000;
		Integer area = req.getAreaMin() != null ? req.getAreaMin() : 0;
		Integer rooms = req.getRoomsMin() != null ? req.getRoomsMin() : 0;
		Integer bathrooms = req.getBathroomsMin() != null ? req.getBathroomsMin() : 0;

		boxToView.addAttribute("requestFromController", new Request(checkin, checkout, price, area, rooms, bathrooms));
		boxToView.addAttribute("apartmentList",
				apartmentRepository.fetchApartments(checkin, checkout, price, area, rooms, bathrooms));

		return "lowfeerent.html";
	}

	@RequestMapping("/admin")
	public String getAdminConsole(Model boxToView) {

		boxToView.addAttribute("apartmentList", apartmentRepository.findAll());

		return "admin.html";
	}

}
