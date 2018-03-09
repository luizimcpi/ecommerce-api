package com.concrete.ecommerce.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.repositories.PedidoRepository;
import com.concrete.ecommerce.api.services.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

	@Autowired
	private PedidoRepository pedidoRepository;

	public Page<Pedido> buscarPorUsuarioId(Long usuarioId, PageRequest pageRequest) {
		log.info("Buscando pedidos para o usuário ID {}", usuarioId);
		return this.pedidoRepository.findByUsuarioId(usuarioId, pageRequest);
	}
	
	@Cacheable("pedidoPorId")
	public Optional<Pedido> buscarPorId(Long id) {
		log.info("Buscando um pedido pelo ID {}", id);
		return Optional.ofNullable(this.pedidoRepository.findOne(id));
	}
	
	@CachePut("pedidoPorId")
	public Pedido persistir(Pedido pedido) {
		log.info("Persistindo o pedido: {}", pedido);
		return this.pedidoRepository.save(pedido);
	}
	
	public void remover(Long id) {
		log.info("Removendo o pedido ID {}", id);
		this.pedidoRepository.delete(id);
	}

}
