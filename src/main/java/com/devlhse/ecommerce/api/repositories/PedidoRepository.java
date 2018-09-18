package com.devlhse.ecommerce.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.devlhse.ecommerce.api.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "PedidoRepository.findByUsuarioId", 
				query = "SELECT pedido FROM Pedido pedido WHERE pedido.usuario.id = :usuarioId") })
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByUsuarioId(@Param("usuarioId") Long usuarioId);

	Page<Pedido> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
}
