package com.project_sem4.admin.service;

import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface AccountService {
    Account getDetail(String email);

    Account register(Account account);

    Account login(String email, String password);

    Account update(String email, Account updateAccount);

    Account getByEmail(String email);

    Page<Account> getList(int page, int limit);

    Optional<Account> findByFullName(String fullname);

    Optional<Account> findByEmail(String email);

    Optional<Account> findForId(Long accountId);

    void update(Long accountId, Account accountDetails);

    Account findById(Long accountId);

    Page<Account> getAll();
    Page<Account> findAllActiveAccount(Specification specification, Pageable pageable);
}
