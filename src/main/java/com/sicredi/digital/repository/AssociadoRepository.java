package com.sicredi.digital.repository;

import com.sicredi.digital.entity.Associado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository  extends CrudRepository<Associado, Long> {
}
