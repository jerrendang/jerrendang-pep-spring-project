package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account account){
    // - The registration will be successful if and only if the username is not blank, 
    //      the password is at least 4 characters long, and an Account with that username does not already exist. 
    //      If all these conditions are met, the response body should contain a JSON of the Account, including its accountId. 
    //      The response status should be 200 OK, which is the default. The new account should be persisted to the database.

    // - If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
    // - If the registration is not successful for some other reason, the response status should be 400. (Client error)
        if (accountService.getAccountByUsername(account.getUsername()) != null){
            return ResponseEntity.status(409).body(null);
        }

        // username not blank
        // password at least 4 long
        // status 200
        if (account.getPassword().length() < 4){
            return ResponseEntity.status(400).body(null);
        }

        Account newAccount = accountService.createAccount(account);
        if (newAccount != null){
            return ResponseEntity.status(200).body(newAccount);
        }  
        return ResponseEntity.status(400).body(null);
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account){
        // status code 200 if successful and return account
        // status code 401 if unsuccessful

        Account loggedIn = accountService.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());

        if (loggedIn == null){
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.status(200).body(loggedIn);
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> newMessage(@RequestBody Message message){
        // message_text not blank and under 255 characters
        // posted_by should be a real user
        // status code 200 if successful and return message
        // status code 400 if unsuccessful

        String messageText = message.getMessageText();
        Account postedBy = accountService.getAccountById(message.getPostedBy());

        if (messageText.length() > 0 && messageText.length() < 255 && postedBy != null){
            Message newMessage = messageService.createMessage(message);
            if (newMessage != null){
                return ResponseEntity.status(200).body(newMessage);
            }
        }

        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable Integer accountId){
        // status code 200
        // body - list of messages
        List<Message> allMessages = messageService.getMessagesByAccountId(accountId);

        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        // status code 200
        // body - list of messages
        List<Message> messages = messageService.getAllMessages();;

        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        // status code 200 
        // body - message
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }
    
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        // status code 200
        // body - number of rows affected
        Integer numRowsAffected = messageService.deleteMessageById(messageId);
        return ResponseEntity.status(200).body(numRowsAffected);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message Tmessage){
        // message exists
        // message_text not blank and under 255 characters
        // status code 200 if successful
        // body - num rows affected
        // status code 400 if unsuccessful
        String message_text = Tmessage.getMessageText();
        Message message = messageService.getMessageById(messageId);

        if (message != null && message_text.length() > 0 && message_text.length() < 255){
                int numRowsAffected = messageService.updateMessageById(messageId, message_text);
                return ResponseEntity.status(200).body(numRowsAffected);
        }

        return ResponseEntity.status(400).body(null);
    }
}
