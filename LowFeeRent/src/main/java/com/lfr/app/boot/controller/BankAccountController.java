package com.lfr.app.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.javafaker.Faker;
import com.lfr.app.boot.model.BankAccount;
import com.lfr.app.boot.utils.Utils;
import com.lfr.app.repository.BankAccountRepository;


@Controller
@RequestMapping("/bankAccount")
public class BankAccountController {

	@Autowired
	BankAccountRepository bankAccountRepository;

	@RequestMapping("/allBankAccounts")
	public String getAllBankAccounts(Model boxToView) {

		boxToView.addAttribute("bankAccountListfromControllerAndDB", bankAccountRepository.findAll());

		return "bankAccounts";
	}

	@RequestMapping("/fillBankAccounts")
	public String fillAllBankAccounts(Model boxToView) {

		Faker faker = new Faker();

		System.out.print("\n---------------- Add bankAccounts: ----------------");
		int n = 1;
		while (n <= 10) {
			BankAccount bankAccount = new BankAccount(faker.finance().iban(), Utils.randRange(5, 55) * 100);

			System.out.print("\n#" + n + " ");
			System.out.print(bankAccount);
			n++;
			bankAccount = bankAccountRepository.save(bankAccount);

		}

		boxToView.addAttribute("bankAccountListfromControllerAndDB", bankAccountRepository.findAll());

		return "bankAccounts";
	}

}
