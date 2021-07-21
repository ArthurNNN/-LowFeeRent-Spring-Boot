package com.lfr.app.boot.controller;

import java.security.Principal;
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
	public String home(Principal prin, Model boxToView, Request req) {

		if (prin == null) {
			boxToView.addAttribute("currentuser", "noBodyHome");
		} else {
			boxToView.addAttribute("currentuser", prin.getName());
		}

		if (isFirstRender) {
			apartmentRepository = ApartmentController.fillInRandomApt(apartmentRepository, 32);

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

		return "lowfeerent";
	}

	@RequestMapping("/admin")
	public String getAdminConsole(Model boxToView) {

		boxToView.addAttribute("apartmentList", apartmentRepository.findAll());

		return "admin";
	}

}
