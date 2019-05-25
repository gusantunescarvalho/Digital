package com.sicredi.digital.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Votacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean ativa;
    private Date dataHoraAtivacao;
    private Long duracao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "voto_id")
    private Set<Voto> votos = new LinkedHashSet<>();

    public Votacao(boolean ativa, Date dataHoraAtivacao, Long duracao, Pauta pauta, Set<Voto> votos) {
        this.ativa = ativa;
        this.dataHoraAtivacao = dataHoraAtivacao;
        this.duracao = duracao;
        this.pauta = pauta;
        this.votos = votos;
    }
}
