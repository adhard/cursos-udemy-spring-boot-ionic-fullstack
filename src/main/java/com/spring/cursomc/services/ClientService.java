package com.spring.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.cursomc.domain.Address;
import com.spring.cursomc.domain.City;
import com.spring.cursomc.domain.Client;
import com.spring.cursomc.domain.enums.Perfil;
import com.spring.cursomc.domain.enums.TipoCliente;
import com.spring.cursomc.dto.ClientDTO;
import com.spring.cursomc.dto.ClientNewDTO;
import com.spring.cursomc.repositories.AddressRepository;
import com.spring.cursomc.repositories.ClientRepository;
import com.spring.cursomc.security.UserSS;
import com.spring.cursomc.services.exceptions.AuthorizationException;
import com.spring.cursomc.services.exceptions.DataIntegrityException;
import com.spring.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Client find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName())); 
	}
	
	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Client update(Client obj) {
		Client newObj = this.find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui pedidos em andamento");
		}
	}
	
	public List<Client> findAll(){
		return repo.findAll();
	}
	
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Client fromDTO(ClientDTO objDto) { 
		return new Client(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Client fromDTO(ClientNewDTO objDto) { 
		Client cli = new Client(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		City cid = new City(objDto.getCidadeId(), null, null);
		
		Address end = new Address(null, objDto.getLogradouro(), 
				objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(),
				objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());			
		}
		
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());			
		}
		
		return cli;
	}
	
	private void updateData(Client newObj, Client obj) {
		// TODO Auto-generated method stub
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
	
}
