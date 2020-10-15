package com.spring.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.spring.cursomc.domain.Client;
import com.spring.cursomc.dto.ClientDTO;
import com.spring.cursomc.repositories.ClientRepository;
import com.spring.cursomc.resources.exceptions.FieldMessage;


public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClientDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClientRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClientDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
	
		System.out.println("OBJDTO email" + objDto.getEmail());
		Client aux = repo.findByEmail(objDto.getEmail());
		
		System.out.println("AUX >"+ aux);
//		System.out.println("AUX ID "+ aux.getId());
//		System.out.println("EQUALS > " + aux.getId().equals(uriId));
		
		if(aux == null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
