package com.project_sem4.admin.config;


import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.getByEmail(email);
        if (account == null){
            throw  new UsernameNotFoundException("user not fount");
        }
        return User.builder().username(account.getEmail()).password(account.getPassword()).roles(account.getRole()).build();
    }
}
