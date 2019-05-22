package com.sicredi.digital.service;

import com.sicredi.digital.entity.Associado;
import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.repository.VotacaoRepository;
import com.sicredi.digital.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotacaoService {

    private final VotacaoRepository votacaoRepository;
    private final VotoRepository votoRepository;

    @Autowired
    public VotacaoService(VotacaoRepository votacaoRepository,
                          VotoRepository votoRepository) {
        this.votacaoRepository = votacaoRepository;
        this.votoRepository = votoRepository;
    }

    public Votacao createVotacao(Pauta pauta) {
        Votacao votacao = new Votacao();
        votacao.setPauta(pauta);
        return this.votacaoRepository.save(votacao);
    }


}
