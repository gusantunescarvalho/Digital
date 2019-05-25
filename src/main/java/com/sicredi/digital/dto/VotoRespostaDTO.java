package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Voto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class VotoRespostaDTO {

    private Long id;
    private AssociadoRespostaDTO associado;
    private boolean parecer;

    public static VotoRespostaDTO transformaEmDTO(Voto voto) {
        return new VotoRespostaDTO(voto.getId(), AssociadoRespostaDTO.transformaEmDTO(voto.getAssociado()), voto.isParecer());
    }
}
