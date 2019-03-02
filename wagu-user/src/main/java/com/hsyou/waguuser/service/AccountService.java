package com.hsyou.waguuser.service;

import com.hsyou.waguuser.model.Account;
import com.hsyou.waguuser.model.AccountDTO;
import com.hsyou.waguuser.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO getAccount(long id){
        Optional<Account> optAccount = accountRepository.findById(id);
        if(optAccount.isPresent()){
            return optAccount.get().toDTO();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found.");
        }
    }

    public Optional<Account> getAccountByUid(String uid){
        return accountRepository.findAccountByUid(uid);
    }
    public Account saveAccount(AccountDTO accountDTO){
        return accountRepository.save(accountDTO.toEntity());
    }

    public AccountDTO getOrSaveAccount(AccountDTO accountDTO){
        Optional<Account> optAccount = getAccountByUid(accountDTO.getUid());
        if(optAccount.isPresent()){
            return optAccount.get().toDTO();
        }else{
            AccountDTO accnt = saveAccount(accountDTO).toDTO();
            accnt.setFirstLogin(true);
            return accnt;
        }
    }
}
