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

    @RequestMapping(method = RequestMethod.POST, value="votacao")
    public Votacao createVotacao(@RequestParam("pautaId") Long pautaId) {
        return votacaoService.createVotacao(pautaId);
    }

    @RequestMapping(method = RequestMethod.GET, value="votacao/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VotacaoResult getVotacao(@PathVariable("id") Long id) {
        return votacaoService.getTotal(id);
    }

    @RequestMapping(method = RequestMethod.POST, value="voto")
    public Votacao createVoto(@RequestParam("votacaoId") Long votacaoId,
                              @RequestParam("associadoId") Long associadoId,
                              @RequestParam("parecer") boolean parecer) {
        return votacaoService.addVoto(votacaoId, associadoId, parecer);
    }
}
