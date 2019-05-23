package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Votacao;

public class VotacaoResult {

    private Long totalFavor;
    private Long totalContra;
    private Votacao votacao;

    public Long getTotalFavor() {
        return totalFavor;
    }

    public void setTotalFavor(Long totalFavor) {
        this.totalFavor = totalFavor;
    }

    public Long getTotalContra() {
        return totalContra;
    }

    public void setTotalContra(Long totalContra) {
        this.totalContra = totalContra;
    }

    public Votacao getVotacao() {
        return votacao;
    }

    public void setVotacao(Votacao votacao) {
        this.votacao = votacao;
    }
}
