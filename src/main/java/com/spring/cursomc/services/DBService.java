package com.spring.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.cursomc.domain.Address;
import com.spring.cursomc.domain.Category;
import com.spring.cursomc.domain.City;
import com.spring.cursomc.domain.Client;
import com.spring.cursomc.domain.ItemPedido;
import com.spring.cursomc.domain.Order;
import com.spring.cursomc.domain.Payment;
import com.spring.cursomc.domain.PaymentBoleto;
import com.spring.cursomc.domain.PaymentCartao;
import com.spring.cursomc.domain.Product;
import com.spring.cursomc.domain.State;
import com.spring.cursomc.domain.enums.EstadoPagamento;
import com.spring.cursomc.domain.enums.Perfil;
import com.spring.cursomc.domain.enums.TipoCliente;
import com.spring.cursomc.repositories.AddressRepository;
import com.spring.cursomc.repositories.CategoryRepository;
import com.spring.cursomc.repositories.CityRepository;
import com.spring.cursomc.repositories.ClientRepository;
import com.spring.cursomc.repositories.ItemPedidoRepository;
import com.spring.cursomc.repositories.OrderRepository;
import com.spring.cursomc.repositories.PaymentRepository;
import com.spring.cursomc.repositories.ProductRepository;
import com.spring.cursomc.repositories.StateRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instantiateTestDatabase() throws ParseException {
		Category cat1 = new Category(null, "Informatica");
		Category cat2 = new Category(null, "Escritorio");
		Category cat3 = new Category(null, "Cama, Mesa e Banho");
		Category cat4 = new Category(null, "Eletronicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");
		
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajour", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2)); 
		p3.getCategorias().addAll(Arrays.asList(cat1)); 
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		State est1 = new State(null, "Minas Gerais");
		State est2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlandia", est1);
		City c2 = new City(null, "São Paulo", est2);
		City c3 = new City(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		stateRepository.saveAll(Arrays.asList(est1, est2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Client cli1 = new Client(null, "Adriano Hardtke", "adr.hardtke@gmail.com", "034.760.480-39", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("24565410","4567897"));
		
		Client cli2 = new Client(null, "Isabel Cousen", "adrghardtke@gmail.com", "034.760.480-39", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().addAll(Arrays.asList("3231211","0800432343"));
		
		Address e1 = new Address(null, "Rua flores", "300", "apt 10", "Jardim", "322544", cli1, c1);
		Address e2 = new Address(null, "Avenida Matos", "105", "sala 800", "Centro", "456489", cli1, c2);
		Address e3 = new Address(null, "Avenida Floriano", "21a", null, "Centro", "874587", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2)); 
		cli2.getEnderecos().addAll(Arrays.asList(e3)); 
		
		clientRepository.saveAll(Arrays.asList(cli1, cli2));
		addressRepository.saveAll(Arrays.asList(e1, e2, e3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Order ped1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Order ped2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Payment pagTo1 = new PaymentCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagTo1);

		Payment pagTo2 = new PaymentBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagTo2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		orderRepository.saveAll(Arrays.asList(ped1, ped2));
		
		paymentRepository.saveAll(Arrays.asList(pagTo1, pagTo2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
