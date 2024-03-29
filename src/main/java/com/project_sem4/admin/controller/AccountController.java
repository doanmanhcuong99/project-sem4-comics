package com.project_sem4.admin.controller;


import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.entity.Story;
import com.project_sem4.admin.service.AccountService;
import com.project_sem4.admin.service.StoryService;
import com.project_sem4.admin.specification.AccountSpecification;
import com.project_sem4.admin.specification.SearchCriteria;
import com.project_sem4.admin.specification.StorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    StoryService storyService;

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String showLoginPage(@Valid Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sign-up")
    public String createAccount(Model model) {
        model.addAttribute("account", new Account());
        return "/registration";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sign-up")
    public String storeAccount(@Valid Account account, BindingResult bindingResult, Model model) {
        if (accountService.findByEmail(account.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.account",
                            "There is already a user registered with the email provided");
        }
        if (accountService.findByFullName(account.getFullname()).isPresent()) {
            bindingResult
                    .rejectValue("fullname", "error.account",
                            "There is already a user registered with the username provided");
        }

        if (!bindingResult.hasErrors()) {
            accountService.register(account);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("account", new Account());
        }
        return "/registration";
    }

    //?
    @RequestMapping(method = RequestMethod.GET, value = "/{email}")
    public ResponseEntity<Account> detail(@PathVariable String email) {
        Account account = accountService.getByEmail(email);
        if (account == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String showListAccount(Model model) {
        model.addAttribute("list", accountService.getList(1, 10));
        return "account/list";

    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") long id, Model model) {
        Account account = accountService.findById(id);
        model.addAttribute("account", account);
        return "accounts/edit";
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") long accountId, Account account) {
        accountService.update(accountId, account);
        return "redirect:/allAccount";
    }

    @GetMapping(value = "/allAccount")
    public String showAllAccount(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            Model model) {
        Specification specification = Specification.where(null);
        if (keyword != null && keyword.length() > 0) {
            specification = specification
                    .and(new AccountSpecification(new SearchCriteria("keyword", "join", keyword)));
            model.addAttribute("keyword", keyword);
        }
        Page<Account> accountPage = accountService.findAllActiveAccount(specification, PageRequest.of(page - 1, limit));
        model.addAttribute("keyword", keyword);
        model.addAttribute("accounts", accountPage.getContent());
        model.addAttribute("currentPage", accountPage.getPageable().getPageNumber() + 1);
        model.addAttribute("limit", accountPage.getPageable().getPageSize());
        model.addAttribute("totalPage", accountPage.getTotalPages());
        return "accounts/list";
    }

    @RequestMapping(value = "/account/show/{id}")
    public String showDetail(@PathVariable("id") long id, Model model) {
        Account account = accountService.findById(id);
        model.addAttribute("account", account);
        return "accounts/showById";
    }
}
