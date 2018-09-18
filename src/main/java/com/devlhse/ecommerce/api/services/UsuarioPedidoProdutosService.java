package com.devlhse.ecommerce.api.services;

import java.util.List;

import com.devlhse.ecommerce.api.entities.Pedido;
import com.devlhse.ecommerce.api.entities.Produto;
import com.devlhse.ecommerce.api.entities.UsuarioPedidoProdutos;

public interface UsuarioPedidoProdutosService {

	UsuarioPedidoProdutos persistir(UsuarioPedidoProdutos usuarioPedidoProdutos);

	void persistirDadosEmLote(List<Produto> produtos, Pedido pedido);

	List<UsuarioPedidoProdutos> buscarPorUsuarioPedidoId(Long usuarioId, Long pedidoId);

	List<Produto> getProdutosDoPedido(Pedido pedido);

}
