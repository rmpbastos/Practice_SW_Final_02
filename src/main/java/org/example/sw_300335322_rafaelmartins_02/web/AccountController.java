package org.example.sw_300335322_rafaelmartins_02.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.sw_300335322_rafaelmartins_02.dto.InvestmentProjection;
import org.example.sw_300335322_rafaelmartins_02.entities.Account;
import org.example.sw_300335322_rafaelmartins_02.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@AllArgsConstructor
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public String displayIndexPage(Model model, HttpSession session) {

        List<Account> accountList = accountRepository.findAll();
        model.addAttribute("accountList", accountList);

        // Check for error message in session
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        return "index";

    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {

        model.addAttribute("account", new Account());

        return "formAdd";

    }

    @PostMapping("/save")
    public String save(Account account, BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "formAdd";
        }

        // Add error message if user tries to add an existing customerNumber
        if (accountRepository.existsByCustomerNumber(account.getCustomerNumber())) {
            session.setAttribute("errorMessage", "The record you are trying to add is already existing. Choose a different customer number.");
            return "redirect:/";
        }

        accountRepository.save(account);

        // Remove the error message from the session after a successful save
        session.removeAttribute("errorMessage");

        return "redirect:/";
    }

    @GetMapping("/editAccount")
    public String editAccount(Model model, Long id, HttpSession session) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        model.addAttribute("account", account);
        model.addAttribute("accountId", id);

        return "editAccount";

    }


    @PostMapping("/saveEdit")
    public String saveEdit(Account account, BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes, Long accountId, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "editAccount";
        }

        // Ensure we update the existing account rather than creating a new one
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        existingAccount.setCustomerNumber(account.getCustomerNumber());
        existingAccount.setCustomerName(account.getCustomerName());
        existingAccount.setCustomerDeposit(account.getCustomerDeposit());
        existingAccount.setNumberOfYears(account.getNumberOfYears());
        existingAccount.setSavingsType(account.getSavingsType());

        accountRepository.save(existingAccount);

        // Remove the error message from the session after a successful edit
        session.removeAttribute("errorMessage");

        return "redirect:/";
    }


    @GetMapping("/delete")
    public String delete(Long id) {

        accountRepository.deleteById(id);

        return "redirect:/";

    }

    @GetMapping("/projectedInvestment")
    public String projectedInvestment(@RequestParam Long id, Model model) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<InvestmentProjection> projections = calculateProjections(account);

        model.addAttribute("account", account);
        model.addAttribute("projections", projections);

        return "projectedInvestment";
    }

    private List<InvestmentProjection> calculateProjections(Account account) {
        double rate = account.getSavingsType().equals("Savings-Deluxe") ? 0.15 : 0.10;
        double amount = account.getCustomerDeposit();
        int years = account.getNumberOfYears();

        List<InvestmentProjection> projections = new ArrayList<>();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        for (int i = 1; i <= years; i++) {
            double interest = amount * rate;
            double endingBalance = amount + interest;
            projections.add(new InvestmentProjection(i, currencyFormatter.format(amount), currencyFormatter.format(interest), currencyFormatter.format(endingBalance)));
            amount = endingBalance;
        }

        return projections;
    }









}