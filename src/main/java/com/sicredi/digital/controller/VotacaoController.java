package com.sicredi.digital.controller;

import com.sicredi.digital.dto.VotacaoResult;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.service.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class VotacaoController {

    private final VotacaoService votacaoService;

    @Autowired
    public VotacaoController(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }

    /**
     * Cria uma nova Sessão de Votação para uma dada Pauta. Esta terá um tempo de duração que é definido pelo timeout.
     * Só será possível adicionar votos na Sessão se a mesma estiver ativa, ou seja, dentro do tempo de duração.
     * @param pautaId identificador da Pauta em que uma Sessão de Votação será criada.
     * @param timeout tempo de duração da Sessão de Votação em segundos. Default = 60 segundos.
     * @return a Sessão de Votação criada.
     */
    @RequestMapping(method = RequestMethod.POST, value="votacao")
    public Votacao createVotacao(@RequestParam("pautaId") Long pautaId, @RequestParam(value="timeout", required = false, defaultValue = "60") Long timeout) {
        Votacao votacao = votacaoService.createVotacao(pautaId, timeout);
        votacaoService.initSession(votacao.getId(), timeout);
        return votacao;
    }

    /**
     * Retorna uma Sessão de Votação pelo seu identificador.
     * @param id identificador da Sessão de Votação.
     * @return objeto contendo a Sessão de Votação mais uma contagem de votos a FAVOR e CONTRA desta sessão.
     */
    @RequestMapping(method = RequestMethod.GET, value="votacao/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VotacaoResult getVotacao(@PathVariable("id") Long id) {
        return votacaoService.getVotacao(id);
    }

    /**
     * Adiciona um voto de um Associado em uma dada Sessão de Votação.
     * @param votacaoId identificador da Sessão de Votação.
     * @param associadoId identificador do Associado.
     * @param parecer se a FAVOR (true) ou CONTRA (false).
     * @return a Sessão de Votação com o novo voto computado.
     */
    @RequestMapping(method = RequestMethod.POST, value="voto")
    public Votacao addVoto(@RequestParam("votacaoId") Long votacaoId,
                           @RequestParam("associadoId") Long associadoId,
                           @RequestParam("parecer") boolean parecer) {
        return votacaoService.addVoto(votacaoId, associadoId, parecer);
    }
}
