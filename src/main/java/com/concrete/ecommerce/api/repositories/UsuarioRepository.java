package com.concrete.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.concrete.ecommerce.api.entities.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByCpf(String cpf);
	
	Usuario findByEmail(String email);
	
	Usuario findByCpfOrEmail(String cpf, String email);
}
