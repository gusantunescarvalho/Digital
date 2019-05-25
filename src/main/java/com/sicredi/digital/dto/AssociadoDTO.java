package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Associado;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AssociadoDTO {

    @NotBlank(message = "{associado.nome.not.blank}")
    private String nome;

    @NotBlank(message = "{associado.cpf.not.blank}")
    private String cpf;

    @NotBlank(message = "{associado.email.not.blank}")
    @Email(message = "{email.not.valid}")
    private String email;

    public Associado transformaParaObjeto(){
        return new Associado(nome, cpf, email);
    }
}
