package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Pauta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PautaDTO {

    private Long id;
    private String titulo;
    private String descricao;

    public Pauta transformaParaObjeto(){
        return new Pauta(titulo, descricao);
    }
}
