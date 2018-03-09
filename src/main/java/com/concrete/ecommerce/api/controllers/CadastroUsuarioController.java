package com.concrete.ecommerce.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concrete.ecommerce.api.dtos.CadastroUsuarioDto;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.enums.PerfilEnum;
import com.concrete.ecommerce.api.response.Response;
import com.concrete.ecommerce.api.services.UsuarioService;
import com.concrete.ecommerce.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-usuario")
@CrossOrigin(origins = "*")
public class CadastroUsuarioController {

	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;

	public CadastroUsuarioController() {
	}

	/**
	 * Cadastra um usuário no sistema.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroUsuarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<CadastroUsuarioDto>> cadastrar(@Valid @RequestBody CadastroUsuarioDto cadastroUsuarioDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando Usuario: {}", cadastroUsuarioDto.toString());
		Response<CadastroUsuarioDto> response = new Response<CadastroUsuarioDto>();

		validarDadosExistentes(cadastroUsuarioDto, result);
		Usuario usuario = this.converterDtoParaUsuario(cadastroUsuarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro Usuario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.usuarioService.persistir(usuario);

		response.setData(this.converterCadastroUsuarioDto(usuario));
		return ResponseEntity.ok(response);
	}

	/**
	 * Verifica se o usuário não existe na base de dados.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result) {
		this.usuarioService.buscarPorEmail(cadastroUsuarioDto.getEmail())
			.ifPresent(func -> result.addError(new ObjectError("usuario", "Email já existente.")));
	}

	/**
	 * Converte os dados do DTO para usuário.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return Usuario
	 * @throws NoSuchAlgorithmException
	 */
	private Usuario converterDtoParaUsuario(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Usuario usuario = new Usuario();
		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
		usuario.setSenha(PasswordUtils.gerarBCrypt(cadastroUsuarioDto.getSenha()));

		return usuario;
	}

	/**
	 * Popula o DTO de cadastro com os dados do usuario.
	 * 
	 * @param usuario
	 * @return cadastroUsuarioDto
	 */
	private CadastroUsuarioDto converterCadastroUsuarioDto(Usuario usuario) {
		CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto();
		cadastroUsuarioDto.setId(usuario.getId());
		cadastroUsuarioDto.setNome(usuario.getNome());
		cadastroUsuarioDto.setEmail(usuario.getEmail());

		return cadastroUsuarioDto;
	}

}
