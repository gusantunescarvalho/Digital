package com.sicredi.digital.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotacaoResultadoRespostaDTO {

    private Long totalFavor;
    private Long totalContra;
    private VotacaoRespostaDTO votacao;
}
