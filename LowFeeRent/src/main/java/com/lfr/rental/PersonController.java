package com.lfr.rental;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.javafaker.Faker;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonRepository personRepository;

	@RequestMapping("/allPersons")
	public String getAllPersons(Model boxToView) {

		boxToView.addAttribute("personListfromControllerAndDB", personRepository.findAll());

		return "person.html";
	}

	@RequestMapping("/fillIn10Persons")
	public String fillAllPersons(Model boxToView) {

		Faker faker = new Faker();

		System.out.print("\n---------------- Add persons: ----------------");
		int n = 1;
		while (n <= 10) {
			Person person = new Person();
			person.setName(faker.name().firstName());
			person.setSurname(faker.name().lastName());
			person.setEmail(faker.internet().emailAddress());

			personRepository.save(person);
			System.out.print("\n#" + n + " ");
			System.out.print(person);
			n++;

		}

		boxToView.addAttribute("personListfromControllerAndDB", personRepository.findAll());

		return "redirect:/person/allPersons";
	}

	// -----------------------delete----------------------------------
	@RequestMapping("/deletePerson")
	public String removePerson(String id, Model model) {

		System.out.println("inside removePerson" + id);
		Optional<Person> personFound = personRepository.findById(id);

		System.out.println("find inside removePerson" + personFound.get());

		if (personFound.isPresent()) {

			personRepository.deleteById(id);
			model.addAttribute("message", "done");
			model.addAttribute("personDeleted", personFound.get());
		}

		else {
			model.addAttribute("message", "error");
		}

		System.out.println("finishing removePerson" + id);
		return "deletedperson.html";
	}

	@RequestMapping("/deleteAllPersons")
	public String deleteAllPersons() {

		personRepository.deleteAll();

		return "redirect:/person/allPersons";

	}

	// -----------------------update----------------------------------
	@RequestMapping("/updatePerson")
	public String updateEmpoyee(String id, Model model) {

		Optional<Person> personFound = findOnePersonById(id);

		if (personFound.isPresent()) {

			model.addAttribute("personfromController", personFound.get());
			return "updateperson";
		}

		else
			return "notfound.html";
	}

	@PostMapping("/replacePerson/{idFromView}")
	public String replacePerson(@PathVariable("idFromView") String id, Person person) {

		Optional<Person> personFound = findOnePersonById(id);

		if (personFound.isPresent()) {

			if (person.getName() != null)
				personFound.get().setName(person.getName());
			if (person.getSurname() != null)
				personFound.get().setSurname(person.getSurname());
//				if (person.getPassword() != null)
//					personFound.get().setPassword(person.getPassword());
			if (person.getEmail() != null)
				personFound.get().setEmail(person.getEmail());
//				if (person.getAge() != 0)
//					personFound.get().setAge(person.getAge());
//				if (person.getMonthSalary() != 0.0)
//					personFound.get().setMonthSalary(person.getMonthSalary());

			personRepository.save(personFound.get());
			return "redirect:/person/allPersons";

		} else
			return "notfound.html";

	}

	// -----------------------detail----------------------------------
	@RequestMapping("/detailPerson")
	public String detailEmpoyee(String id, Model model) {

		Optional<Person> personFound = findOnePersonById(id);

		if (personFound.isPresent()) {

			model.addAttribute("personfromController", personFound.get());
			return "detailperson";
		}

		else
			return "notfound.html";
	}

	// --------------------------------------------------------------------------------
	// ------------------------- service to controller
	// --------------------------------
	// --------------------------------------------------------------------------------

	public Optional<Person> findOnePersonById(String id) {

		// System.out.println("inside findPerson" + id);
		Optional<Person> personFound = personRepository.findById(id);
		// System.out.println("finishing findPerson" + id);
		// System.out.println("finishing findPerson" + personFound.get());
		return personFound;
	}

}
