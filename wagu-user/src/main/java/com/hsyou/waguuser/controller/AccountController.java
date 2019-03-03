package com.hsyou.waguuser.controller;

import com.hsyou.waguuser.model.Account;
import com.hsyou.waguuser.model.AccountDTO;
import com.hsyou.waguuser.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    //getOrSaveAccount
    @PostMapping("/account")
    public AccountDTO getOrSaveAccount(@RequestBody AccountDTO accountDTO){

        return accountService.getOrSaveAccount(accountDTO);
    }
    //isvalid

    //get info

    @GetMapping("/")
    public AccountDTO getAccount(@RequestParam long accountId){

        return accountService.getAccount(accountId);
    }


    //update info

}
