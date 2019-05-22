package com.sicredi.digital.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Votacao {

    private Pauta pauta;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "voto_id")
    private Set<Voto> votos = new LinkedHashSet<>();

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Set<Voto> getVotos() {
        return votos;
    }

    public void setVotos(Set<Voto> votos) {
        this.votos = votos;
    }

    public void addVoto(Voto voto) {
        votos.add(voto);
        voto.setVotacao(this);
    }

    public void removeVoto(Voto voto) {
        votos.remove(voto);
        voto.setVotacao(null);
    }
}
