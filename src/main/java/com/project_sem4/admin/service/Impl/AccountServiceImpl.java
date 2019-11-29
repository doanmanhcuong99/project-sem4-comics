package com.project_sem4.admin.service.Impl;


import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.pagination.PageModel;
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
    @Autowired
    PageModel pageModel;

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

    @Override
    public Optional<Account> findForId(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void update(Long accountId, Account accountDetails) {
        Account currentAccount = findById(accountId);
        currentAccount.setFullname(accountDetails.getFullname());
        currentAccount.setAddress(accountDetails.getAddress());
        accountRepository.save(currentAccount);
    }

    @Override
    public Account findById(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (!accountOptional.isPresent()) {
            throw new RuntimeException("Account Not Found!");
        }
        return accountOptional.get();
    }
    @Override
    public Page<Account> getAll() {
        pageModel.setSIZE(5);
        pageModel.initPageAndSize();
        return accountRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));

    }

}
