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

    @GetMapping("/account/{id}")
    public AccountDTO getAccount(@PathVariable long id, @RequestParam String userId){
        System.out.println(userId);
        return accountService.getAccount(id);
    }


    //update info

}
