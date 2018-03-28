package com.concrete.ecommerce.api.components;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.concrete.ecommerce.api.dtos.PedidoDto;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.services.UsuarioService;

@Component
public class UsuarioValidatorComponent {

	private static final Logger log = LoggerFactory.getLogger(UsuarioValidatorComponent.class);

	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioValidatorComponent(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * Valida um usuário, verificando se ele é existente e válido no sistema.
	 * 
	 * @param pedidoDto
	 * @param result
	 */
	public void validarUsuario(PedidoDto pedidoDto, BindingResult result) {
		if (pedidoDto.getUsuarioId() == null) {
			result.addError(new ObjectError("usuario", "Usuário não informado."));
			return;
		}

		log.info("Validando usuário id {}: ", pedidoDto.getUsuarioId());
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(pedidoDto.getUsuarioId());
		if (!usuario.isPresent()) {
			result.addError(new ObjectError("usuario", "Usuário não encontrado. ID inexistente."));
		}
	}
}
