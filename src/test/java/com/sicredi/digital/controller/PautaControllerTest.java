package com.sicredi.digital.controller;

import com.sicredi.digital.AbstractTest;
import com.sicredi.digital.dto.PautaDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PautaControllerTest extends AbstractTest {

    /**
     * Teste criar nova Pauta.
     * @throws Exception
     */
    @Test
    public void t01_criarNovaPautaTest() throws Exception {

        String uri = "/pauta";

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Nova Previdência");
        pautaDTO.setDescricao("Votação para aprovar a nova previdência");

        mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pautaDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.titulo").value("Nova Previdência"))
                .andExpect(jsonPath("$.descricao").value("Votação para aprovar a nova previdência"));
    }

    /**
     * Teste pegar uma Pauta existente pelo identificador
     * @throws Exception
     */
    @Test
    public void t02_getPautaExistenteTest() throws Exception {

        String uri = "/pauta/{id}";

        mvc.perform(MockMvcRequestBuilders.get(uri, "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.titulo").value("Nova Previdência"))
                .andExpect(jsonPath("$.descricao").value("Votação para aprovar a nova previdência"));
    }

    /**
     * Teste pegar uma Pauta inexistente pelo identificador
     * @throws Exception
     */
    @Test
    public void t03_getPautaInexistenteTest() throws Exception {

        String uri = "/pauta/{id}";

        mvc.perform(MockMvcRequestBuilders.get(uri, "2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Teste pegar todas as Pautas.
     * @throws Exception
     */
    @Test
    public void t04_getTodasPautasTest() throws Exception {

        String uri = "/pauta";

        mvc.perform(MockMvcRequestBuilders.get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].titulo").value("Nova Previdência"))
                .andExpect(jsonPath("$.[0].descricao").value("Votação para aprovar a nova previdência"));
    }

    /**
     * Teste deletar uma Pauta inexistente pelo identificador
     * @throws Exception
     */
    @Test
    public void t05_deletarPautaInexistenteTest() throws Exception {

        String uri = "/pauta/{id}";

        mvc.perform(MockMvcRequestBuilders.delete(uri, "2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Teste deletar uma Pauta existente pelo identificador
     * @throws Exception
     */
    @Test
    public void t06_deletarPautaExistenteTest() throws Exception {

        String uri = "/pauta/{id}";

        mvc.perform(MockMvcRequestBuilders.delete(uri, "1"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }
}
