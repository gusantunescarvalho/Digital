package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Voto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotoDTO {

    private Long id;
    private AssociadoDTO associado;
    private boolean parecer;

    public Voto transformaParaObjeto(){
        return new Voto(associado.transformaParaObjeto(), parecer);
    }
}
