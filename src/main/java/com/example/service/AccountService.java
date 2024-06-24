package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findByUsername(username)
            .orElse(null);
    }

    public Account getAccountById(int accountId){
        return accountRepository.findById(accountId)
            .orElse(null);
    }

    public Account getAccountByUsernameAndPassword(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password)
            .orElse(null);
    }
}
