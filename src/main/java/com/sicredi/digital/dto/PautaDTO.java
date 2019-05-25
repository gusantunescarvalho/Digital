package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Pauta;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PautaDTO {

    @NotBlank(message = "{pauta.titulo.not.blank}")
    private String titulo;

    private String descricao;

    public Pauta transformaParaObjeto(){
        return new Pauta(titulo, descricao);
    }
}
