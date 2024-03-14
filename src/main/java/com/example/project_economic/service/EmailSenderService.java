package com.example.project_economic.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    String sendEmail(Long userId);
}
