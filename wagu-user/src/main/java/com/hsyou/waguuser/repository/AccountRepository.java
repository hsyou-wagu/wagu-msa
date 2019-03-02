package com.hsyou.waguuser.repository;

import com.hsyou.waguuser.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUid(String uid);
}
