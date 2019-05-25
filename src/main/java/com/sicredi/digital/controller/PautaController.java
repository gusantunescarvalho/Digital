package com.sicredi.digital.controller;

import com.sicredi.digital.dto.PautaDTO;
import com.sicredi.digital.dto.PautaRespostaDTO;
import com.sicredi.digital.entity.Pauta;
import com.sicredi.digital.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    private final PautaRepository pautaRepository;

    @Autowired
    public PautaController(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    /**
     * Retorna uma lista de todas as Pautas.
     * @return lista de todas as Pautas.
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Pauta> findAll() {
        return pautaRepository.findAll();
    }

    /**
     * Retorna uma Pauta pelo seu identificador.
     * @param id identificador da Pauta.
     * @return a Pauta conforme identificador.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<PautaRespostaDTO> findById(@PathVariable Long id) {
        Optional<Pauta> pauta = pautaRepository.findById(id);
        HttpEntity response = pauta.isPresent() ? new ResponseEntity<>(PautaRespostaDTO.transformaEmDTO(pauta.get()), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return response;
    }

    /**
     * Cria uma nova Pauta.
     * @param pautaDTO a Pauta para criar.
     * @return a Pauta criada.
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<PautaRespostaDTO> create(@RequestBody @Valid PautaDTO pautaDTO) {
        Pauta pauta = pautaRepository.save(pautaDTO.transformaParaObjeto());
        return new ResponseEntity<>(PautaRespostaDTO.transformaEmDTO(pauta), HttpStatus.CREATED);
    }

    /**
     * Deleta uma Pauta pelo seu identificador.
     * @param id identificador da Pauta.
     * @return código HTTP de resposta de acordo.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity delete(@PathVariable Long id) {
        Optional<Pauta> pauta = pautaRepository.findById(id);
        pauta.ifPresent( p -> pautaRepository.delete(p));
        return pauta.isPresent() ? new ResponseEntity<>(HttpStatus.ACCEPTED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
