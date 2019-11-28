package com.project_sem4.admin.service.Impl;


import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.repository.AccountRepository;
import com.project_sem4.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Account getDetail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    public Account register(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole("user");
        return accountRepository.save(account);
    }

    public Account login(String email, String password) {
        // find user
        Optional<Account> optionalAccoun = accountRepository.findByEmail(email);
        if (optionalAccoun.isPresent()) {
            // so sanh pass co kem ma hoa
            Account account = optionalAccoun.get();
            if (account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public Account update(String email, Account updateAccount) {
        // tim tai khoan xem co ton tai
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            Account existAccount = optionalAccount.get();
            existAccount.setFullname(updateAccount.getFullname());
            existAccount.setAddress(updateAccount.getAddress());
            return accountRepository.save(updateAccount);
        }
        return null;

    }

    public Account getByEmail(String email) {

        return accountRepository.findByEmail(email).orElse(null);
    }

    public Page<Account> getList(int page, int limit) {
        return accountRepository.findAll(PageRequest.of(page - 1, limit));

    }

    @Override
    public Optional<Account> findByFullName(String fullname) {
        return accountRepository.findByFullname(fullname);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
