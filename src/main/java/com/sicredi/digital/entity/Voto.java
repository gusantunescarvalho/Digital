package com.sicredi.digital.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "associado_id")
    private Associado associado;

    private boolean parecer;

    public Voto(Associado associado, boolean parecer) {
        this.associado = associado;
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
