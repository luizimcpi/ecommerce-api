package com.concrete.ecommerce.api.components;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.concrete.ecommerce.api.dtos.PedidoDto;
import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.services.UsuarioPedidoProdutosService;

@Component
public class PedidoConversorComponent {
	
	private final UsuarioPedidoProdutosService usuarioPedidoProdutosService;
	
	@Autowired
	public PedidoConversorComponent(final UsuarioPedidoProdutosService usuarioPedidoProdutosService) {
		this.usuarioPedidoProdutosService = usuarioPedidoProdutosService;
	}

	public Pedido converterDtoParaPedido(PedidoDto pedidoDto, BindingResult result) throws ParseException {
		Pedido pedido = new Pedido();
		pedido.setUsuario(new Usuario());
		pedido.getUsuario().setId(pedidoDto.getUsuarioId());
		
		if(pedidoDto.getProdutos() == null || pedidoDto.getProdutos().isEmpty()) {
			result.addError(new ObjectError("pedido", "Lista de Produtos não pode estar vazia."));
		}

		pedido.setDescricao(pedidoDto.getDescricao());
		pedido.setEnderecoEntrega(pedidoDto.getEnderecoEntrega());
		
		return pedido;
	}
	
	
	public PedidoDto converterPedidoDto(Pedido pedido) {
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setId(Optional.of(pedido.getId()));
		pedidoDto.setDescricao(pedido.getDescricao());
		pedidoDto.setEnderecoEntrega(pedido.getEnderecoEntrega());
		pedidoDto.setUsuarioId(pedido.getUsuario().getId());
		pedidoDto.setProdutos(usuarioPedidoProdutosService.getProdutosDoPedido(pedido));
		pedidoDto.setValorTotal(pedido.getValorTotal());
		
		return pedidoDto;
	}

}
