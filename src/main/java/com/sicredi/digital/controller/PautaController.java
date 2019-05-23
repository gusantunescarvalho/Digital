package com.sicredi.digital.controller;

import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    private final PautaRepository pautaRepository;

    @Autowired
    public PautaController(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Pauta> findAll() {
        return pautaRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<Pauta> findById(@PathVariable Long id) {
        Optional<Pauta> pauta = pautaRepository.findById(id);
        HttpEntity response = pauta.isPresent() ? new ResponseEntity<>(pauta.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Pauta create(@RequestBody Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestBody Pauta pauta) {
        pautaRepository.delete(pauta);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity delete(@PathVariable Long id) {
        Optional<Pauta> pauta = pautaRepository.findById(id);
        pauta.ifPresent( p -> pautaRepository.delete(p));
        return pauta.isPresent() ? new ResponseEntity<>(HttpStatus.ACCEPTED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
