package com.devlhse.ecommerce.api.repositories;

import java.util.List;

import com.devlhse.ecommerce.api.entities.UsuarioPedidoProdutos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UsuarioPedidoProdutosRepository extends JpaRepository<UsuarioPedidoProdutos, Long> {

	List<UsuarioPedidoProdutos> findByUsuarioIdAndPedidoId(Long usuarioId, Long pedidoId);
}
