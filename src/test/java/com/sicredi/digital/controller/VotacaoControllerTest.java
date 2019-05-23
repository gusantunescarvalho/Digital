package com.sicredi.digital.controller;

import com.sicredi.digital.AbstractTest;
import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.service.VotacaoService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VotacaoControllerTest extends AbstractTest {

    @Autowired
    private VotacaoService votacaoService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * Teste criar nova Sessão de Votação com uma Pauta existente.
     * @throws Exception
     */
    @Test
    public void t01_criarNovaVotacaoComPautaExistenteComTimeoutTest() throws Exception {

        String uri = "/votacao";


        Date agora = Calendar.getInstance().getTime();

        Pauta pauta = new Pauta();
        pauta.setId(new Long(1));
        pauta.setTitulo("Nova Previdência");
        pauta.setDescricao("Votação para aprovar a nova previdência");

        Votacao votacao = new Votacao();
        votacao.setId(new Long(1));
        votacao.setPauta(pauta);
        votacao.setDuracao(new Long(10));
        votacao.setDataHoraAtivacao(null);
        votacao.setAtiva(true);

        when(votacaoService.createVotacao(new Long(1), new Long(10))).thenReturn(votacao);
        doNothing().when(votacaoService).initSession(new Long(1), new Long(10));


        mvc.perform(MockMvcRequestBuilders.post(uri)
                .param("pautaId", "1")
                .param("timeout", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(votacao)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.ativa").value("true"))
                .andExpect(jsonPath("$.duracao").value("10"))
                .andExpect(jsonPath("$.dataHoraAtivacao").value(null))
                .andExpect(jsonPath("$.pauta.id").value("1"))
                .andExpect(jsonPath("$.pauta.titulo").value("Nova Previdência"))
                .andExpect(jsonPath("$.pauta.descricao").value("Votação para aprovar a nova previdência"));

    }

}
