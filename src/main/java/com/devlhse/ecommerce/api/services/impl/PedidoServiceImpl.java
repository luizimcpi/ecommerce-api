package com.devlhse.ecommerce.api.services.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.devlhse.ecommerce.api.components.PedidoConversorComponent;
import com.devlhse.ecommerce.api.components.UsuarioValidatorComponent;
import com.devlhse.ecommerce.api.dtos.PedidoDto;
import com.devlhse.ecommerce.api.entities.Pedido;
import com.devlhse.ecommerce.api.entities.Produto;
import com.devlhse.ecommerce.api.repositories.PedidoRepository;
import com.devlhse.ecommerce.api.services.PedidoService;
import com.devlhse.ecommerce.api.services.UsuarioPedidoProdutosService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

	private final PedidoRepository pedidoRepository;
	private final PedidoConversorComponent pedidoConversorComponent;
	private final UsuarioPedidoProdutosService usuarioPedidoProdutosService;
	private final UsuarioValidatorComponent usuarioValidatorComponent;

	@Autowired
	public PedidoServiceImpl(final PedidoRepository pedidoRepository,
			final PedidoConversorComponent pedidoConversorComponent,
			final UsuarioPedidoProdutosService usuarioPedidoProdutosService,
			final UsuarioValidatorComponent usuarioValidatorComponent) {
		this.pedidoRepository = pedidoRepository;
		this.pedidoConversorComponent = pedidoConversorComponent;
		this.usuarioPedidoProdutosService = usuarioPedidoProdutosService;
		this.usuarioValidatorComponent = usuarioValidatorComponent;
	}

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
	public Pedido persistir(PedidoDto pedidoDto, Pedido pedido) {
		log.info("Persistindo o pedido: {}", pedido);
		pedido.setValorTotal(this.calculaValorTotal(pedidoDto.getProdutos()));
		Pedido pedidoBD = this.pedidoRepository.save(pedido);
		if (pedidoBD != null) {
			usuarioPedidoProdutosService.persistirDadosEmLote(pedidoDto.getProdutos(), pedidoBD);
		}
		return pedidoBD;
	}

	public void remover(Long id) {
		log.info("Removendo o pedido ID {}", id);
		this.pedidoRepository.delete(id);
	}

	@Override
	public PedidoDto converterPedidoDto(Pedido pedido) {
		return pedidoConversorComponent.converterPedidoDto(pedido);
	}

	@Override
	public Pedido converterDtoParaPedido(PedidoDto pedidoDto, BindingResult result) throws ParseException {
		return pedidoConversorComponent.converterDtoParaPedido(pedidoDto, result);
	}

	@Override
	public void validarUsuario(PedidoDto pedidoDto, BindingResult result) {
		usuarioValidatorComponent.validarUsuario(pedidoDto, result);
	}

	private double calculaValorTotal(List<Produto> produtos) {
		double total = 0;
		if(produtos != null && !produtos.isEmpty()) {
			for (Produto produto : produtos) {
				total += produto.getValor();
			}
		}
		return total;
	}
}
