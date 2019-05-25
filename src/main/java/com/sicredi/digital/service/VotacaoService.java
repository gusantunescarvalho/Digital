package com.sicredi.digital.service;

import com.sicredi.digital.dto.VotacaoRespostaDTO;
import com.sicredi.digital.dto.VotacaoResultadoRespostaDTO;
import com.sicredi.digital.entity.Associado;
import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.entity.Voto;
import com.sicredi.digital.repository.AssociadoRepository;
import com.sicredi.digital.repository.PautaRepository;
import com.sicredi.digital.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class VotacaoService {

    private final AssociadoRepository associadoRepository;
    private final PautaRepository pautaRepository;
    private final VotacaoRepository votacaoRepository;

    @Autowired
    public VotacaoService(AssociadoRepository associadoRepository,
                          PautaRepository pautaRepository,
                          VotacaoRepository votacaoRepository) {
        this.associadoRepository = associadoRepository;
        this.pautaRepository = pautaRepository;
        this.votacaoRepository = votacaoRepository;
    }

    /**
     * Cria uma nova Sessão de Votação para uma dada Pauta. Esta terá um tempo de duração que é definido pelo timeout.
     * Só será possível adicionar votos na Sessão se a mesma estiver ativa, ou seja, dentro do tempo de duração.
     * @param pautaId identificador da Pauta em que uma Sessão de Votação será criada.
     * @param timeout tempo de duração da Sessão de Votação em segundos. Default = 60 segundos.
     * @return a Sessão de Votação criada.
     * @throws IllegalArgumentException se o identificador da Pauta não existir.
     */
    public VotacaoRespostaDTO createVotacao(Long pautaId, Long timeout) throws IllegalArgumentException {

        Optional<Pauta> pauta = pautaRepository.findById(pautaId);
        if (pauta.isPresent()) {
            Votacao votacao = new Votacao();
            votacao.setDataHoraAtivacao(Calendar.getInstance().getTime());
            votacao.setAtiva(true);
            votacao.setDuracao(timeout);
            votacao.setPauta(pauta.get());
            Votacao votacaoPersist = this.votacaoRepository.save(votacao);
            this.initSession(votacao.getId(), timeout);
            return VotacaoRespostaDTO.transformaEmDTO(votacaoPersist);
        } else {
            throw new IllegalArgumentException("Pauta não existe");
        }
    }

    /**
     * Inicia a contagem do tempo em que uma Sessão de Votação permanecerá ativa.
     * Enquanto a Sessão estiver dentro do tempo de duração, será possível adicionar votos para esta sessão.
     * Após expirar o tempo de duração, a sessão torna-se inativa e não será mais possível adicionar votos na mesma.
     * @param votacaoId identificador da Sessão de Votação.
     * @param timeout tempo de duração da Sessão de Votação.
     */
    @Async("fileExecutor")
    public void initSession(Long votacaoId, Long timeout){
        try {
            Thread.sleep( Long.parseLong(timeout + "000"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Optional<Votacao> votacao = votacaoRepository.findById(votacaoId);
        votacao.ifPresent( v-> { v.setAtiva(false); votacaoRepository.save(votacao.get()); });
    }

    /**
     * Retorna uma Sessão de Votação pelo seu identificador.
     * @param votacaoId identificador da Sessão de Votação.
     * @return objeto contendo a Sessão de Votação mais uma contagem de votos a FAVOR e CONTRA desta sessão.
     */
    public VotacaoResultadoRespostaDTO getVotacaoResultado(Long votacaoId) {

        Optional<Votacao> votacao = votacaoRepository.findById(votacaoId);
        VotacaoResultadoRespostaDTO votacaoResultadoRespostaDTO = new VotacaoResultadoRespostaDTO();
        if (votacao.isPresent()) {
            votacaoResultadoRespostaDTO.setVotacao(VotacaoRespostaDTO.transformaEmDTO(votacao.get()));
            votacaoResultadoRespostaDTO.setTotalFavor(votacao.get().getVotos().stream().filter(voto -> voto.isParecer()).count());
            votacaoResultadoRespostaDTO.setTotalContra(votacao.get().getVotos().stream().filter(voto -> !voto.isParecer()).count());
        }
        return votacaoResultadoRespostaDTO;
    }

    /**
     * Adiciona um voto de um Associado em uma dada Sessão de Votação.
     * @param votacaoId identificador da Sessão de Votação.
     * @param associadoId identificador do Associado.
     * @param parecer se a FAVOR (true) ou CONTRA (false).
     * @return a Sessão de Votação com o novo voto computado.
     * @throws IllegalArgumentException se o identificador do Associado ou Sessão de Votação não existir ou se a sessão
     * já estiver sido encerrada.
     */
    public VotacaoRespostaDTO addVoto(Long votacaoId, Long associadoId, boolean parecer) throws IllegalArgumentException{

        Optional<Associado> associado = associadoRepository.findById(associadoId);
        if (associado.isPresent()) {
            Voto voto = new Voto();
            voto.setAssociado(associado.get());
            voto.setParecer(parecer);

            Optional<Votacao> votacao = votacaoRepository.findById(votacaoId);
            if (votacao.isPresent() && votacao.get().isAtiva()) {
                votacao.get().getVotos().add(voto);
                Votacao votacaoPersist = this.votacaoRepository.save(votacao.get());
                return VotacaoRespostaDTO.transformaEmDTO(votacaoPersist);
            } else {
                throw new IllegalArgumentException("Sessão de votação não existe ou já encerrada");
            }
        } else {
            throw new IllegalArgumentException("Associado não existe");
        }
    }

}
