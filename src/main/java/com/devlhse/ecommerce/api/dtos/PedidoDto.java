package com.devlhse.ecommerce.api.dtos;

import java.util.List;
import java.util.Optional;

import com.devlhse.ecommerce.api.entities.Produto;

public class PedidoDto {

	private Optional<Long> id = Optional.empty();
	private String descricao;
	private String enderecoEntrega;
	private Long usuarioId;
	private List<Produto> produtos;
	private double valorTotal;

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

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public String toString() {
		return "PedidoDto [id=" + id + ", descricao=" + descricao + ", enderecoEntrega=" + enderecoEntrega
				+ ", usuarioId=" + usuarioId + ", produtos=" + produtos + ", valorTotal=" + valorTotal + "]";
	}

}
