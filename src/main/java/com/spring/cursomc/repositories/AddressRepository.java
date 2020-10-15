package com.spring.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.cursomc.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
	
}
