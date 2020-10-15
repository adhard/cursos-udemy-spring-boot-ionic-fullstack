package com.spring.cursomc.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.spring.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PaymentCartao extends Payment {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;
	
	public PaymentCartao() {}

	public PaymentCartao(Integer id, EstadoPagamento estado, Order pedido, Integer numeroDeparcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeparcelas;
		// TODO Auto-generated constructor stub
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
	
}
