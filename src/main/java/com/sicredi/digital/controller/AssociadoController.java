package com.sicredi.digital.controller;

import com.sicredi.digital.entity.Associado;
import com.sicredi.digital.entity.Votacao;
import com.sicredi.digital.repository.AssociadoRepository;
import com.sicredi.digital.service.VotacaoService;
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

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Associado> findAll() {
        return associadoRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<Associado> findById(@PathVariable Long id) {
        Optional<Associado> associado = associadoRepository.findById(id);
        HttpEntity response = associado.isPresent() ? new ResponseEntity<>(associado.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Associado create(@RequestBody Associado associado) {
        return associadoRepository.save(associado);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestBody Associado associado) {
        associadoRepository.delete(associado);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity delete(@PathVariable Long id) {
        Optional<Associado> associado = associadoRepository.findById(id);
        associado.ifPresent( a -> associadoRepository.delete(a));
        return associado.isPresent() ? new ResponseEntity<>(HttpStatus.ACCEPTED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
