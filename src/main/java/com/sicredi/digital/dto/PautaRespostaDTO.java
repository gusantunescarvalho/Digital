package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Pauta;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PautaRespostaDTO {

    private Long id;
    private String titulo;
    private String descricao;

    public static PautaRespostaDTO transformaEmDTO(Pauta pauta) {
        return new PautaRespostaDTO(pauta.getId(), pauta.getTitulo(), pauta.getDescricao());
    }
}
