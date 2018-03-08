package com.concrete.ecommerce.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.ecommerce.api.entities.Pedido;
import com.concrete.ecommerce.api.repositories.ProdutoRepository;
import com.concrete.ecommerce.api.services.PedidoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@MockBean
	private ProdutoRepository lancamentoRepository;

	@Autowired
	private PedidoService lancamentoService;

	@Before
	public void setUp() throws Exception {
		BDDMockito
				.given(this.lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Pedido>(new ArrayList<Pedido>()));
		BDDMockito.given(this.lancamentoRepository.findOne(Mockito.anyLong())).willReturn(new Pedido());
		BDDMockito.given(this.lancamentoRepository.save(Mockito.any(Pedido.class))).willReturn(new Pedido());
	}

	@Test
	public void testBuscarLancamentoPorFuncionarioId() {
		Page<Pedido> lancamento = this.lancamentoService.buscarPorFuncionarioId(1L, new PageRequest(0, 10));

		assertNotNull(lancamento);
	}

	@Test
	public void testBuscarLancamentoPorId() {
		Optional<Pedido> lancamento = this.lancamentoService.buscarPorId(1L);

		assertTrue(lancamento.isPresent());
	}

	@Test
	public void testPersistirLancamento() {
		Pedido lancamento = this.lancamentoService.persistir(new Pedido());

		assertNotNull(lancamento);
	}

}
