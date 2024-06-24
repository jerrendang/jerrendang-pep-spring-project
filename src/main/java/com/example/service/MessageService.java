package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id){
        return messageRepository.findById(id)
            .orElse(null);
    }

    public Integer deleteMessageById(Integer id){
        if (this.getMessageById(id) == null){
            return null;
        }
        messageRepository.deleteById(id);

        return 1;
    }

    public Integer updateMessageById(Integer messageId, String message_text){
        Message message = this.getMessageById(messageId);

        message.setMessageText(message_text);

        messageRepository.save(message);

        return 1;
    }

    public List<Message> getMessagesByAccountId(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
