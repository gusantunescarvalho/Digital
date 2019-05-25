package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Associado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociadoDTO {

    private String nome;
    private String cpf;
    private String email;

    public Associado transformaParaObjeto(){
        return new Associado(nome, cpf, email);
    }
}
