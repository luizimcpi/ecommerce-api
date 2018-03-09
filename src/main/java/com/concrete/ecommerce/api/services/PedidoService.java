package com.concrete.ecommerce.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.concrete.ecommerce.api.entities.Pedido;

public interface PedidoService {

	/**
	 * Retorna uma lista paginada de pedidos de um determinado usuário.
	 * 
	 * @param usuarioId
	 * @param pageRequest
	 * @return Page<Lancamento>
	 */
	Page<Pedido> buscarPorUsuarioId(Long usuarioId, PageRequest pageRequest);
	
	/**
	 * Retorna um lançamento por ID.
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 */
	Optional<Pedido> buscarPorId(Long id);
	
	/**
	 * Persiste um lançamento na base de dados.
	 * 
	 * @param lancamento
	 * @return Lancamento
	 */
	Pedido persistir(Pedido lancamento);
	
	/**
	 * Remove um pedido da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
	
}
