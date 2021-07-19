package com.lfr.app.boot.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;
import com.lfr.app.boot.model.Apartment;
import com.lfr.app.boot.utils.Utils;
import com.lfr.app.repository.ApartmentRepository;

@Controller
@RequestMapping("/apartment")
public class ApartmentController {

	@Autowired
	ApartmentRepository apartmentRepository;

	@RequestMapping("/fillin32apartments")
	public String fillInApartments(Model boxToView) {
		apartmentRepository = fillInRandomApt(apartmentRepository, 32);

		boxToView.addAttribute("apartmentList", apartmentRepository.findAll());

		return "redirect:/apartment/allApartments";
	}

	protected static ApartmentRepository fillInRandomApt(ApartmentRepository apartmentRepository, int qt) {
		Faker faker = new Faker();
		int n = 1;
		while (n <= qt) {
			Apartment apartment = new Apartment(Utils.randRange(8, 25) * 100, Utils.randRange(6, 18) * 10,
					Utils.randRange(1, 5), Utils.randRange(1, 3), faker.address().streetAddress(true));
			System.out.println(apartment);
			
			apartment.setImgUrl("../a/" + (apartment.id % 33) + ".jpg");
			System.out.println(apartment);
			

			Map<LocalDate, LocalDate> datesMap = new HashMap<LocalDate, LocalDate>();
			datesMap.put(LocalDate.of(2021, Utils.randRange(1, 3), Utils.randRange(1, 28)),
					LocalDate.of(2021, Utils.randRange(3, 5), Utils.randRange(1, 28)));
			datesMap.put(LocalDate.of(2021, Utils.randRange(5, 6), Utils.randRange(1, 28)),
					LocalDate.of(2021, Utils.randRange(6, 9), Utils.randRange(1, 28)));
			datesMap.put(LocalDate.of(2021, Utils.randRange(9, 10), Utils.randRange(1, 28)),
					LocalDate.of(2021, Utils.randRange(10, 13), Utils.randRange(1, 28)));

			System.out.println(datesMap);
			apartment.setOpenDates(datesMap);

			apartmentRepository.save(apartment);
			n++;
		}
		return apartmentRepository;
	}

	// -----------------------add----------------------------------
	@RequestMapping("/newApartment")
	public String newApartment() {

		return "newapartment";
	}

	@RequestMapping("/addApartment")
	public String insertApartment(Apartment apartment) {

		apartmentRepository.save(apartment);

		return "redirect:/apartment/allApartments";
	}

	@RequestMapping("/allApartments")
	public String getApartments(Model boxToView) {

		boxToView.addAttribute("apartmentList", apartmentRepository.findAll());

		return "apartment";
	}

	// -----------------------update----------------------------------
	@RequestMapping("/updateApartment")
	public String updateApartment(int id, Model model) {

		Optional<Apartment> apartmentFound = apartmentRepository.findById(id);

		if (apartmentFound.isPresent()) {

			model.addAttribute("apartmentfromController", apartmentFound.get());
			return "updateapartment";
		}

		else
			return "notfound";
	}

	@PostMapping("/replaceApartment/{idFromView}")
	public String replaceApartment(@PathVariable("idFromView") int id, Apartment apartment) {

		Optional<Apartment> apartmentFound = apartmentRepository.findById(id);

		if (apartmentFound.isPresent()) {

			if (apartment.getAddress() != null)
				apartmentFound.get().setAddress(apartment.getAddress());
			if (apartment.getPrice() != 0)
				apartmentFound.get().setPrice(apartment.getPrice());
			if (apartment.getArea() != 0)
				apartmentFound.get().setArea(apartment.getArea());
			if (apartment.getRooms() != 0)
				apartmentFound.get().setRooms(apartment.getRooms());
			if (apartment.getBathrooms() != 0)
				apartmentFound.get().setBathrooms(apartment.getBathrooms());
			if (apartment.getLessorId() != null)
				apartmentFound.get().setLessorId(apartment.getLessorId());

			apartmentRepository.save(apartmentFound.get());
			return "redirect:/apartment/allApartments";

		} else
			return "notfound";

	}

	// -----------------------detail----------------------------------
	@RequestMapping("/detailApartment")
	public String detailApartment(int id, Model model) {

		Optional<Apartment> apartmentFound = apartmentRepository.findById(id);

		if (apartmentFound.isPresent()) {

			model.addAttribute("apartmentfromController", apartmentFound.get());
			return "detailapartment";
		}

		else
			return "notfound";
	}

	// -----------------------delete----------------------------------
	@RequestMapping("/deleteApartment")
	public String removeApartment(int id, Model model) {

		System.out.println("inside removeApartment" + id);
		Optional<Apartment> apartmentFound = apartmentRepository.findById(id);

		System.out.println("find inside removeApartment" + apartmentFound.get());

		if (apartmentFound.isPresent()) {

			apartmentRepository.deleteById(id);
			model.addAttribute("message", "done");
			model.addAttribute("apartmentDeleted", apartmentFound.get());
		}

		else {
			model.addAttribute("message", "error");
		}

		System.out.println("finishing removeApartment" + id);
		return "deletedapartment";
	}

	@RequestMapping("/deleteAllApartments")
	public String deleteAllApartments() {

		apartmentRepository.deleteAll();

		return "redirect:/apartment/allApartments";

	}

}
