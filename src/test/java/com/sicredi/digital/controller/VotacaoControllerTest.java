package com.sicredi.digital.controller;

import com.sicredi.digital.AbstractTest;
import com.sicredi.digital.dto.VotacaoResult;
import com.sicredi.digital.entity.Associado;
import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.entity.Voto;
import com.sicredi.digital.service.VotacaoService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VotacaoControllerTest extends AbstractTest {

    @MockBean
    private VotacaoService votacaoService;

    /**
     * Teste criar nova Sessão de Votação com uma Pauta existente.
     * @throws Exception
     */
    @Test
    public void t01_criarNovaVotacaoTest() throws Exception {

        String uri = "/votacao";

        Pauta pauta = new Pauta();
        pauta.setId(new Long(1));
        pauta.setTitulo("Nova Previdência");
        pauta.setDescricao("Votação para aprovar a nova previdência");

        Votacao votacao = new Votacao();
        votacao.setId(new Long(1));
        votacao.setPauta(pauta);
        votacao.setDuracao(new Long(10));
        votacao.setAtiva(true);

        given(votacaoService.createVotacao(any(Long.class), any(Long.class))).willReturn(votacao);

        MockHttpServletRequestBuilder postRequest = (MockMvcRequestBuilders.post(uri))
                .param("pautaId", "1")
                .param("timeout", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(votacao));

        ResultActions resultActions = mvc.perform(postRequest);

        resultActions.andDo(print())
                .andExpect(status().isOk())
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

        Pauta pauta = new Pauta();
        pauta.setId(new Long(1));
        pauta.setTitulo("Nova Previdência");
        pauta.setDescricao("Votação para aprovar a nova previdência");

        Votacao votacao = new Votacao();
        votacao.setId(new Long(1));
        votacao.setPauta(pauta);
        votacao.setDuracao(new Long(10));
        votacao.setAtiva(true);

        VotacaoResult votacaoResult = new VotacaoResult();
        votacaoResult.setTotalFavor(new Long(12));
        votacaoResult.setTotalContra(new Long(6));
        votacaoResult.setVotacao(votacao);

        given(votacaoService.getVotacao(any(Long.class))).willReturn(votacaoResult);

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

        given(votacaoService.getVotacao(any(Long.class))).willReturn(new VotacaoResult());

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

        String uri = "/voto";

        Pauta pauta = new Pauta();
        pauta.setId(new Long(1));
        pauta.setTitulo("Nova Previdência");
        pauta.setDescricao("Votação para aprovar a nova previdência");

        Associado associado = new Associado();
        associado.setId(new Long(1));

        Voto voto = new Voto();
        voto.setId(new Long(1));
        voto.setAssociado(associado);
        voto.setParecer(true);

        Votacao votacao = new Votacao();
        votacao.setId(new Long(1));
        votacao.setPauta(pauta);
        votacao.setDuracao(new Long(10));
        votacao.setAtiva(true);
        votacao.getVotos().add(voto);

        given(votacaoService.addVoto(any(Long.class), any(Long.class), any(Boolean.class))).willReturn(votacao);

        MockHttpServletRequestBuilder postRequest = (MockMvcRequestBuilders.post(uri))
                .param("votacaoId", "1")
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
                .andExpect(jsonPath("$.votos[0].parecer").value("true"));

    }

}
