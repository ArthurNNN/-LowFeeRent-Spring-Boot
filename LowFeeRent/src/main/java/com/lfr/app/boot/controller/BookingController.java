package com.lfr.app.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lfr.app.repository.BookingRepository;


@Controller
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingRepository bookingRepository;

	@RequestMapping("")
	public String getAllRequests(Model boxToView) {

		boxToView.addAttribute("requestList", bookingRepository.findAll());

		return "home";
	}


}
