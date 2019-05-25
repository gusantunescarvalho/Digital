package com.sicredi.digital.controller;

import com.sicredi.digital.entity.Associado;
import com.sicredi.digital.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/associado")
public class AssociadoController {

    private final AssociadoRepository associadoRepository;

    @Autowired
    public AssociadoController(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    /**
     * Retorna uma lista de todos os Associados.
     * @return lista de todos os Associados.
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Associado> findAll() {
        return associadoRepository.findAll();
    }

    /**
     * Retorna um Associado pelo seu identificador.
     * @param id identificador do Associado.
     * @return o Associado conforme identificador.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<Associado> findById(@PathVariable Long id) {
        Optional<Associado> associado = associadoRepository.findById(id);
        HttpEntity response = associado.isPresent() ? new ResponseEntity<>(associado.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return response;
    }

    /**
     * Cria um novo Associado.
     * @param associado o Associado para criar.
     * @return o Associado criado.
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<Associado> create(@RequestBody Associado associado) {
        Associado associadoNovo = associadoRepository.save(associado);
        return new ResponseEntity<>(associadoNovo, HttpStatus.CREATED);
    }

    /**
     * Deleta um Associado pelo seu identificador.
     * @param id identificador do Associado.
     * @return código HTTP de resposta de acordo.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity delete(@PathVariable Long id) {
        Optional<Associado> associado = associadoRepository.findById(id);
        associado.ifPresent( a -> associadoRepository.delete(a));
        return associado.isPresent() ? new ResponseEntity<>(HttpStatus.ACCEPTED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
