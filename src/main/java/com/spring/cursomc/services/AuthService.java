package com.spring.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.cursomc.domain.Client;
import com.spring.cursomc.repositories.ClientRepository;
import com.spring.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	private Random rand = new Random();

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	public void sendNewPassword(String email) {
		Client cliente = clientRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clientRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		for(int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3); // de 0 a 2
		
		if(opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		
		else if(opt == 0) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
}
