package com.spring.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.cursomc.domain.Client;
import com.spring.cursomc.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{


	@Transactional(readOnly = true)
	Page<Order> findByCliente(Client cliente, Pageable pageRequest);

}
