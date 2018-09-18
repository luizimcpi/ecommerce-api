package com.devlhse.ecommerce.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devlhse.ecommerce.api.entities.Pedido;
import com.devlhse.ecommerce.api.entities.Produto;
import com.devlhse.ecommerce.api.entities.UsuarioPedidoProdutos;
import com.devlhse.ecommerce.api.repositories.UsuarioPedidoProdutosRepository;
import com.devlhse.ecommerce.api.services.ProdutoService;
import com.devlhse.ecommerce.api.services.UsuarioPedidoProdutosService;

@Service
public class UsuarioPedidoProdutosServiceImpl implements UsuarioPedidoProdutosService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioPedidoProdutosServiceImpl.class);

	@Autowired
	private UsuarioPedidoProdutosRepository usuarioPedidoProdutosRepository;

	@Autowired
	private ProdutoService produtoService;

	@Override
	public UsuarioPedidoProdutos persistir(UsuarioPedidoProdutos usuarioPedidoProdutos) {
		log.info("Persistindo o usuarioPedidoProdutos: {}", usuarioPedidoProdutos);
		return this.usuarioPedidoProdutosRepository.save(usuarioPedidoProdutos);
	}

	@Override
	public List<UsuarioPedidoProdutos> buscarPorUsuarioPedidoId(Long usuarioId, Long pedidoId) {
		return this.usuarioPedidoProdutosRepository.findByUsuarioIdAndPedidoId(usuarioId, pedidoId);
	}

	@Override
	public List<Produto> getProdutosDoPedido(Pedido pedido) {
		List<UsuarioPedidoProdutos> listaIdProdutos = this.buscarPorUsuarioPedidoId(pedido.getUsuario().getId(),
				pedido.getId());
		List<Produto> produtos = null;
		if (listaIdProdutos != null && !listaIdProdutos.isEmpty()) {
			produtos = new ArrayList<>();
			for (UsuarioPedidoProdutos usuarioPedidoProdutos : listaIdProdutos) {
				Optional<Produto> produto = produtoService.buscarPorId(usuarioPedidoProdutos.getProdutoId());
				if (produto.isPresent()) {
					produtos.add(produto.get());
				}
			}
		}
		return produtos;
	}

	@Override
	public void persistirDadosEmLote(List<Produto> produtos, Pedido pedido) {
		UsuarioPedidoProdutos usuarioPedidoProdutos = null;
		if (produtos != null && !produtos.isEmpty()) {
			for (Produto produto : produtos) {
				usuarioPedidoProdutos = new UsuarioPedidoProdutos();
				usuarioPedidoProdutos.setUsuarioId(pedido.getUsuario().getId());
				usuarioPedidoProdutos.setPedidoId(pedido.getId());
				usuarioPedidoProdutos.setProdutoId(produto.getId());
				this.persistir(usuarioPedidoProdutos);
			}
		}
	}
}
