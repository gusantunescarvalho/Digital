package com.sicredi.digital.controller;

import com.sicredi.digital.AbstractTest;
import com.sicredi.digital.entity.Associado;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssociadoControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * Teste criar novo Associado.
     * @throws Exception
     */
    @Test
    public void t01_criarNovoAssociadoTest() throws Exception {

        String uri = "/associado";

        Associado associado = new Associado();
        associado.setNome("Gustavo Antunes Carvalho");
        associado.setCpf("222.333.222-33");

        mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(associado)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"));
    }

    /**
     * Teste pegar um Associado existente pelo identificador
     * @throws Exception
     */
    @Test
    public void t02_getAssociadoExistenteTest() throws Exception {

        String uri = "/associado/{id}";

        mvc.perform(MockMvcRequestBuilders.get(uri, "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Gustavo Antunes Carvalho"))
                .andExpect(jsonPath("$.cpf").value("222.333.222-33"));
    }

    /**
     * Teste pegar um Associado inexistente pelo identificador
     * @throws Exception
     */
    @Test
    public void t03_getAssociadoInexistenteTest() throws Exception {

        String uri = "/associado/{id}";

        mvc.perform(MockMvcRequestBuilders.get(uri, "2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Teste pegar todos os Associados.
     * @throws Exception
     */
    @Test
    public void t04_getTodosAssociadosTest() throws Exception {

        String uri = "/associado";

        mvc.perform(MockMvcRequestBuilders.get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].nome").value("Gustavo Antunes Carvalho"))
                .andExpect(jsonPath("$.[0].cpf").value("222.333.222-33"));;
    }

    /**
     * Teste deletar um Associado inexistente pelo identificador
     * @throws Exception
     */
    @Test
    public void t05_deletarAssociadoInexistenteTest() throws Exception {

        String uri = "/associado/{id}";

        mvc.perform(MockMvcRequestBuilders.delete(uri, "2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Teste deletar um Associado existente pelo identificador
     * @throws Exception
     */
    @Test
    public void t06_deletarAssociadoExistenteTest() throws Exception {

        String uri = "/associado/{id}";

        mvc.perform(MockMvcRequestBuilders.delete(uri, "1"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }
}
