package com.sicredi.digital.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.sicredi.digital.entity.Votacao;

@JsonRootName(value="Votacao")
public class VotacaoResult {

    @JsonProperty
    private Long totalFavor;

    @JsonProperty
    private Long totalContra;

    @JsonProperty
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
