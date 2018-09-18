package com.devlhse.ecommerce.api.components;

import java.security.NoSuchAlgorithmException;

import com.devlhse.ecommerce.api.dtos.CadastroUsuarioDto;
import com.devlhse.ecommerce.api.entities.Usuario;
import com.devlhse.ecommerce.api.enums.PerfilEnum;
import com.devlhse.ecommerce.api.utils.PasswordUtils;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConversorComponent {
	
	public Usuario converterDtoParaUsuario(CadastroUsuarioDto cadastroUsuarioDto) throws NoSuchAlgorithmException {
		Usuario usuario = new Usuario();
		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
		usuario.setSenha(PasswordUtils.gerarBCrypt(cadastroUsuarioDto.getSenha()));

		return usuario;
	}
	

	public CadastroUsuarioDto converterUsuarioParaDto(Usuario usuario) {
		CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto();
		cadastroUsuarioDto.setId(usuario.getId());
		cadastroUsuarioDto.setNome(usuario.getNome());
		cadastroUsuarioDto.setEmail(usuario.getEmail());

		return cadastroUsuarioDto;
	}
}
