package com.example.repository;

import org.springframework.stereotype.Repository;
import java.util.*;

import com.example.entity.Account;

@Repository
public interface AccountRepository {
    private List<Account> accountList = new ArrayList<>();

    public List<Account> getAll(){
        
    }
}
