package com.concrete.ecommerce.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.concrete.ecommerce.api.entities.Pedido;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "LancamentoRepository.findByFuncionarioId", 
				query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId") })
public interface ProdutoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

	Page<Pedido> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
