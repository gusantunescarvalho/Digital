package com.sicredi.digital.controller;

import com.sicredi.digital.AbstractTest;
import com.sicredi.digital.dto.*;
import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.entity.Voto;
import com.sicredi.digital.service.VotacaoService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VotacaoControllerTest extends AbstractTest {

    @MockBean
    private VotacaoService votacaoService;

    private PautaRespostaDTO pautaRespostaDTO;
    private VotacaoRespostaDTO votacaoRespostaDTO;

    @Before
    public void setUp() {
        pautaRespostaDTO = new PautaRespostaDTO(1L, "Nova Previdência", "Votação para aprovar a nova previdência");
        votacaoRespostaDTO = new VotacaoRespostaDTO(1L, true,null, 10L, pautaRespostaDTO, new LinkedHashSet<>() );
    }

    /**
     * Teste criar nova Sessão de Votação com uma Pauta existente.
     * @throws Exception
     */
    @Test
    public void t01_criarNovaVotacaoTest() throws Exception {

        String uri = "/votacao";

        given(votacaoService.createVotacao(any(Long.class), any(Long.class))).willReturn(votacaoRespostaDTO);

        MockHttpServletRequestBuilder postRequest = (MockMvcRequestBuilders.post(uri))
                .param("pautaId", "1")
                .param("timeout", "10");

        ResultActions resultActions = mvc.perform(postRequest);

        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.ativa").value("true"))
                .andExpect(jsonPath("$.duracao").value("10"))
                .andExpect(jsonPath("$.pauta.id").value("1"))
                .andExpect(jsonPath("$.pauta.titulo").value("Nova Previdência"))
                .andExpect(jsonPath("$.pauta.descricao").value("Votação para aprovar a nova previdência"));
    }

    /**
     * Teste pegar uma Sessão de Votação existente.
     * @throws Exception
     */
    @Test
    public void t02_getVotacaoExistenteTest() throws Exception {

        String uri = "/votacao/{id}";

        VotacaoResultadoRespostaDTO votacaoResultadoRespostaDTO = new VotacaoResultadoRespostaDTO();
        votacaoResultadoRespostaDTO.setTotalFavor(12L);
        votacaoResultadoRespostaDTO.setTotalContra(6L);
        votacaoResultadoRespostaDTO.setVotacao(votacaoRespostaDTO);

        given(votacaoService.getVotacaoResultado(any(Long.class))).willReturn(votacaoResultadoRespostaDTO);

        mvc.perform(MockMvcRequestBuilders.get(uri, "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.totalFavor").value("12"))
                .andExpect(jsonPath("$.totalContra").value("6"))
                .andExpect(jsonPath("$.votacao.id").value("1"))
                .andExpect(jsonPath("$.votacao.ativa").value("true"))
                .andExpect(jsonPath("$.votacao.duracao").value("10"))
                .andExpect(jsonPath("$.votacao.pauta.id").value("1"))
                .andExpect(jsonPath("$.votacao.pauta.titulo").value("Nova Previdência"))
                .andExpect(jsonPath("$.votacao.pauta.descricao").value("Votação para aprovar a nova previdência"));
    }

    /**
     * Teste pegar uma Sessão de Votação inexistente.
     * @throws Exception
     */
    @Test
    public void t03_getVotacaoInxistenteTest() throws Exception {

        String uri = "/votacao/{id}";

        given(votacaoService.getVotacaoResultado(any(Long.class))).willReturn(new VotacaoResultadoRespostaDTO());

        mvc.perform(MockMvcRequestBuilders.get(uri, "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Teste adicionar um voto numa Sessão de Votação.
     * @throws Exception
     */
    @Test
    public void t04_addVotoTest() throws Exception {

        String uri = "/votacao/{id}";

        AssociadoRespostaDTO associadoRespostaDTO = new AssociadoRespostaDTO(1L, "João da Silva", "111.111.111-11","joao@email.com");
        VotoRespostaDTO votoRespostaDTO = new VotoRespostaDTO(1L, associadoRespostaDTO, true);
        Set<VotoRespostaDTO> votosRespostaDTO = new LinkedHashSet<>();
        votosRespostaDTO.add(votoRespostaDTO);
        votacaoRespostaDTO = new VotacaoRespostaDTO(1L, true,null, 10L, pautaRespostaDTO, votosRespostaDTO );

        given(votacaoService.addVoto(any(Long.class), any(Long.class), any(Boolean.class))).willReturn(votacaoRespostaDTO);

        MockHttpServletRequestBuilder postRequest = (MockMvcRequestBuilders.patch(uri, "1"))
                .param("associadoId", "1")
                .param("parecer", "true");

        ResultActions resultActions = mvc.perform(postRequest);

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.ativa").value("true"))
                .andExpect(jsonPath("$.duracao").value("10"))
                .andExpect(jsonPath("$.pauta.id").value("1"))
                .andExpect(jsonPath("$.pauta.titulo").value("Nova Previdência"))
                .andExpect(jsonPath("$.pauta.descricao").value("Votação para aprovar a nova previdência"))
                .andExpect(jsonPath("$.votos[0].id").value("1"))
                .andExpect(jsonPath("$.votos[0].associado.id").value("1"))
                .andExpect(jsonPath("$.votos[0].associado.nome").value("João da Silva"))
                .andExpect(jsonPath("$.votos[0].associado.cpf").value("111.111.111-11"))
                .andExpect(jsonPath("$.votos[0].associado.email").value("joao@email.com"))
                .andExpect(jsonPath("$.votos[0].parecer").value("true"));

    }

}
