package com.sicredi.digital.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "associado_id")
    private Associado associado;

    private boolean parecer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public boolean isParecer() {
        return parecer;
    }

    public void setParecer(boolean parecer) {
        this.parecer = parecer;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(associado.getCpf());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Voto)) {
            return false;
        }
        Voto that = (Voto) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(associado.getCpf(), that.associado.getCpf());
        return eb.isEquals();
    }
}
