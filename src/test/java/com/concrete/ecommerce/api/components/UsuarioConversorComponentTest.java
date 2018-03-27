package com.concrete.ecommerce.api.components;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.ecommerce.api.dtos.CadastroUsuarioDto;
import com.concrete.ecommerce.api.entities.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioConversorComponentTest {
	
	private static final long ID_VALIDO = 1L;
	private static final String NOME_VALIDO = "Luiz Evangelista";
	private static final String EMAIL_VALIDO = "teste@teste.com.br";
	private static final String SENHA_VALIDA = "teste1234";
	
	@Autowired
	private UsuarioConversorComponent usuarioConversorComponent;
	
	
	@Test
	public void deveConverterDtoParaEntidadeUsuario() throws Exception {
		CadastroUsuarioDto cadastroUsuarioDto = this.preencheDtoUsuario();
		Usuario usuario = this.usuarioConversorComponent.converterDtoParaUsuario(cadastroUsuarioDto);
		assertNotNull(usuario);
	}
	
	@Test
	public void deveConverterUsuarioParaDto() throws Exception {
		Usuario usuario = this.preencheEntidadeUsuario();
		CadastroUsuarioDto cadastroUsuarioDto = this.usuarioConversorComponent.converterUsuarioParaDto(usuario);
		assertNotNull(cadastroUsuarioDto);
	}
	
	private Usuario preencheEntidadeUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(ID_VALIDO);
		usuario.setNome(NOME_VALIDO);
		usuario.setEmail(EMAIL_VALIDO);
		return usuario;
	}

	private CadastroUsuarioDto preencheDtoUsuario() {
		CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto();
		cadastroUsuarioDto.setNome(NOME_VALIDO);
		cadastroUsuarioDto.setEmail(EMAIL_VALIDO);
		cadastroUsuarioDto.setSenha(SENHA_VALIDA);
		return cadastroUsuarioDto;
	}
}
