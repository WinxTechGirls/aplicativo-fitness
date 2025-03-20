package com.generation.fitness.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.fitness.model.Nivel;
import com.generation.fitness.repository.NivelRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/niveis")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class NivelController {

	@Autowired
    private NivelRepository nivelRepository;

    @GetMapping
    public ResponseEntity<List<Nivel>> getAll() {
        return ResponseEntity.ok(nivelRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nivel> getById(@PathVariable Long id) {
        return nivelRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Nivel> post(@Valid @RequestBody Nivel nivel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(nivelRepository.save(nivel));
    }

    @PutMapping
    public ResponseEntity<Nivel> put(@Valid @RequestBody Nivel nivel) {
        return nivelRepository.findById(nivel.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(nivelRepository.save(nivel)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Nivel> usuario = nivelRepository.findById(id);

        if (usuario.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        nivelRepository.deleteById(id);
    }
}

