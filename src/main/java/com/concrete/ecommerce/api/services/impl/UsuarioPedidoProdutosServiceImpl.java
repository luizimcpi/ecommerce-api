package com.concrete.ecommerce.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concrete.ecommerce.api.entities.UsuarioPedidoProdutos;
import com.concrete.ecommerce.api.repositories.UsuarioPedidoProdutosRepository;
import com.concrete.ecommerce.api.services.UsuarioPedidoProdutosService;

@Service
public class UsuarioPedidoProdutosServiceImpl implements UsuarioPedidoProdutosService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioPedidoProdutosServiceImpl.class);

	@Autowired
	private UsuarioPedidoProdutosRepository usuarioPedidoProdutosRepository;

	
	public UsuarioPedidoProdutos persistir(UsuarioPedidoProdutos usuarioPedidoProdutos) {
		log.info("Persistindo o usuarioPedidoProdutos: {}", usuarioPedidoProdutos);
		return this.usuarioPedidoProdutosRepository.save(usuarioPedidoProdutos);
	}
	

}
