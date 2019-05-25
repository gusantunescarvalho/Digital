package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Associado;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AssociadoRespostaDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;

    public static AssociadoRespostaDTO transformaEmDTO(Associado associado) {
        return new AssociadoRespostaDTO(associado.getId(), associado.getNome(), associado.getCpf(), associado.getEmail());
    }
}
