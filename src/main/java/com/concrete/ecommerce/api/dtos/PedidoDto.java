package com.concrete.ecommerce.api.dtos;

import java.util.Optional;

public class PedidoDto {

	private Optional<Long> id = Optional.empty();
	private String descricao;
	private String enderecoEntrega;
	private Long usuarioId;

	public PedidoDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public String toString() {
		return "PedidoDto [id=" + id + ", descricao=" + descricao + ", enderecoEntrega=" + enderecoEntrega
				+ ", usuarioId=" + usuarioId + "]";
	}

}
