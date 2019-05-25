package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Votacao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class VotacaoRespostaDTO {

    private Long id;
    private boolean ativa;
    private Date dataHoraAtivacao;
    private Long duracao;
    private PautaRespostaDTO pauta;
    private Set<VotoRespostaDTO> votos = new LinkedHashSet<>();

    public static VotacaoRespostaDTO transformaEmDTO(Votacao votacao) {
        Set<VotoRespostaDTO> votosDTO = new LinkedHashSet<>();
        votacao.getVotos().stream().map( v -> votosDTO.add( VotoRespostaDTO.transformaEmDTO(v)));
        return new VotacaoRespostaDTO(votacao.getId(), votacao.isAtiva(), votacao.getDataHoraAtivacao(), votacao.getDuracao(), PautaRespostaDTO.transformaEmDTO(votacao.getPauta()), votosDTO);
    }
}
