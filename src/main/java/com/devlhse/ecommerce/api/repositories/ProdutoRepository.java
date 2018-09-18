package com.devlhse.ecommerce.api.repositories;

import com.devlhse.ecommerce.api.entities.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Page<Produto> findAll(Pageable pageable);

}
