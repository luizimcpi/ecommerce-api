package com.devlhse.ecommerce.api.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import com.devlhse.ecommerce.api.dtos.PedidoDto;
import com.devlhse.ecommerce.api.entities.Pedido;
import com.devlhse.ecommerce.api.response.Response;
import com.devlhse.ecommerce.api.services.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

	private final PedidoService pedidoService;
	
	@Autowired
	public PedidoController(final PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;


	/**
	 * Retorna a listagem de pedidos de um usuário.
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<PedidoDto>>
	 */
	@GetMapping(value = "/usuario/{usuarioId}")
	public ResponseEntity<Response<Page<PedidoDto>>> listarPorUsuarioId(@PathVariable("usuarioId") Long usuarioId,
                                                                        @RequestParam(value = "pag", defaultValue = "0") int pag,
                                                                        @RequestParam(value = "ord", defaultValue = "id") String ord,
                                                                        @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando produtos por ID do usuário: {}, página: {}", usuarioId, pag);
		Response<Page<PedidoDto>> response = new Response<Page<PedidoDto>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Pedido> pedidos = this.pedidoService.buscarPorUsuarioId(usuarioId, pageRequest);
		Page<PedidoDto> pedidosDto = pedidos.map(pedido -> pedidoService.converterPedidoDto(pedido));

		response.setData(pedidosDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna um pedido por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<PedidoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<PedidoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando pedido por ID: {}", id);
		Response<PedidoDto> response = new Response<PedidoDto>();
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(id);

		if (!pedido.isPresent()) {
			log.info("Pedido não encontrado para o ID: {}", id);
			response.getErrors().add("Pedido não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(pedidoService.converterPedidoDto(pedido.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona um novo pedido.
	 * 
	 * @param pedido
	 * @param result
	 * @return ResponseEntity<Response<PedidoDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<PedidoDto>> adicionar(@Valid @RequestBody PedidoDto pedidoDto, BindingResult result)
			throws ParseException {
		log.info("Adicionando pedido: {}", pedidoDto.toString());
		Response<PedidoDto> response = new Response<PedidoDto>();
		pedidoService.validarUsuario(pedidoDto, result);
		Pedido pedido = pedidoService.converterDtoParaPedido(pedidoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando pedido: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		pedido = this.pedidoService.persistir(pedidoDto, pedido);
		response.setData(pedidoService.converterPedidoDto(pedido));
		return ResponseEntity.ok(response);
	}

	/**
	 * Remove um pedido por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Pedido>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo pedido: {}", id);
		Response<String> response = new Response<String>();
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(id);

		if (!pedido.isPresent()) {
			log.info("Erro ao remover devido ao pedido ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover pedido. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.pedidoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

}
