package com.sicredi.digital.dto;

import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.entity.Voto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class VotacaoDTO {

    private Long id;
    private boolean ativa;
    private Date dataHoraAtivacao;
    private Long duracao;
    private PautaDTO pauta;
    private Set<VotoDTO> votos = new LinkedHashSet<>();

    public Votacao transformaParaObjeto(){
        Set<Voto> votosObj = new LinkedHashSet<>();
        votos.stream().map( v -> votosObj.add( v.transformaParaObjeto() ));
        return new Votacao(ativa, dataHoraAtivacao, duracao, pauta.transformaParaObjeto(), votosObj);
    }
}
