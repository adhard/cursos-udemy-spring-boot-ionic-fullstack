package com.spring.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cursomc.domain.ItemPedido;
import com.spring.cursomc.domain.Order;
import com.spring.cursomc.domain.PaymentBoleto;
import com.spring.cursomc.domain.enums.EstadoPagamento;
import com.spring.cursomc.repositories.ItemPedidoRepository;
import com.spring.cursomc.repositories.OrderRepository;
import com.spring.cursomc.repositories.PaymentRepository;
import com.spring.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmailService emailService;
	
	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName())); 
	}
	
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clientService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PaymentBoleto) {
			PaymentBoleto pagto = (PaymentBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		paymentRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(productService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		
		emailService.sendOrderConfirmationEmail(obj);
		
		return obj;
	}
	
}
