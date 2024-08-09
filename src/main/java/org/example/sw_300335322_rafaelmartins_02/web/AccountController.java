package org.example.sw_300335322_rafaelmartins_02.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.sw_300335322_rafaelmartins_02.entities.Account;
import org.example.sw_300335322_rafaelmartins_02.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        return "redirect:/";
    }

    @GetMapping("/editAccount")
    public String editAccount(Model model, Long id, HttpSession session) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        model.addAttribute("account", account);
        return "editAccount";
    }


    @PostMapping("/saveEdit")
    public String saveEdit(Account account, BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "editAccount";
        }

        accountRepository.save(account);
        return "redirect:/";
    }


}
