package com.sicredi.digital.repository;

import com.sicredi.digital.entity.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
}
