package com.sicredi.digital.service;

import com.sicredi.digital.dto.VotacaoResult;
import com.sicredi.digital.entity.Associado;
import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.entity.Voto;
import com.sicredi.digital.repository.AssociadoRepository;
import com.sicredi.digital.repository.PautaRepository;
import com.sicredi.digital.repository.VotacaoRepository;
import com.sicredi.digital.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotacaoService {

    private final AssociadoRepository associadoRepository;
    private final PautaRepository pautaRepository;
    private final VotacaoRepository votacaoRepository;
    private final VotoRepository votoRepository;

    @Autowired
    public VotacaoService(AssociadoRepository associadoRepository,
                          PautaRepository pautaRepository,
                          VotacaoRepository votacaoRepository,
                          VotoRepository votoRepository) {
        this.associadoRepository = associadoRepository;
        this.pautaRepository = pautaRepository;
        this.votacaoRepository = votacaoRepository;
        this.votoRepository = votoRepository;
    }

    public Votacao createVotacao(Long pautaId) {

        Optional<Pauta> pauta = pautaRepository.findById(pautaId);
        if (pauta.isPresent()) {
            Votacao votacao = new Votacao();
            votacao.setPauta(pauta.get());
            return this.votacaoRepository.save(votacao);
        } else {
            throw new IllegalArgumentException("Pauta não existe");
        }
    }

    public Votacao addVoto(Long votacaoId, Long associadoId, boolean parecer) {

        Optional<Associado> associado = associadoRepository.findById(associadoId);
        if (associado.isPresent()) {
            Voto voto = new Voto();
            voto.setAssociado(associado.get());
            voto.setParecer(parecer);

            Optional<Votacao> votacao = votacaoRepository.findById(votacaoId);
            if (votacao.isPresent()) {
                votacao.get().addVoto(voto);
                return this.votacaoRepository.save(votacao.get());
            } else {
                throw new IllegalArgumentException("Sessão de votação não existe");
            }
        } else {
            throw new IllegalArgumentException("Associado não existe");
        }
    }

    public VotacaoResult getTotal(Long votacaoId) {

        Optional<Votacao> votacao = votacaoRepository.findById(votacaoId);
        VotacaoResult votacaoResult = new VotacaoResult();
        votacaoResult.setVotacao(votacao.get());
        votacaoResult.setTotalFavor(votacao.get().getVotos().stream().filter(voto -> voto.isParecer()).count());
        votacaoResult.setTotalContra(votacao.get().getVotos().stream().filter(voto -> !voto.isParecer()).count());
        return votacaoResult;
    }

}
