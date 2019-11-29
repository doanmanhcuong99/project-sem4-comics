package com.project_sem4.admin.repository;


import com.project_sem4.admin.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByEmail(@Param("email") String email);

    Optional<Account> findByFullname(@Param("fullname") String fullname);

}
