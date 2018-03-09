package com.concrete.ecommerce.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.repositories.UsuarioRepository;
import com.concrete.ecommerce.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario persistir(Usuario usuario) {
		log.info("Persistindo usuário: {}", usuario);
		return this.usuarioRepository.save(usuario);
	}
	
	public Optional<Usuario> buscarPorEmail(String email) {
		log.info("Buscando usuário pelo email {}", email);
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}
	
	public Optional<Usuario> buscarPorId(Long id) {
		log.info("Buscando usuário pelo IDl {}", id);
		return Optional.ofNullable(this.usuarioRepository.findOne(id));
	}

}
