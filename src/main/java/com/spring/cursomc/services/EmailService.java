package com.spring.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.spring.cursomc.domain.Client;
import com.spring.cursomc.domain.Order;

public interface EmailService {

	
	void sendOrderConfirmationEmail(Order obj);
	
	void sendMail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Client cliente, String newPass);
	
}
