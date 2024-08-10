package org.example.sw_300335322_rafaelmartins_02.web;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.example.sw_300335322_rafaelmartins_02.entities.Account;
import org.example.sw_300335322_rafaelmartins_02.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

class AccountControllerTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        account.setCustomerNumber("1001");
        account.setCustomerName("John Doe");
        account.setCustomerDeposit(1000.0);
        account.setNumberOfYears(5);
        account.setSavingsType("Savings-Regular");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(accountRepository.existsByCustomerNumber("1001")).thenReturn(false);

        String viewName = accountController.save(account, bindingResult, model, redirectAttributes, session);

        assertEquals("redirect:/", viewName);
        verify(accountRepository, times(1)).save(account);
        verify(session, times(1)).removeAttribute("errorMessage");
    }

}