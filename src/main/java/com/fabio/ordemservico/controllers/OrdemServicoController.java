package com.fabio.ordemservico.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fabio.ordemservico.dtos.OrdemServicoDTO;
import com.fabio.ordemservico.services.OrdemServicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/os")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService ordemServicoService;

	// Buscar por id
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoDTO> findById(@PathVariable Integer id) {
		OrdemServicoDTO obj = new OrdemServicoDTO(ordemServicoService.findById(id));
		return ResponseEntity.ok().body(obj);
	}

	// Buscar todos
	@GetMapping
	public ResponseEntity<List<OrdemServicoDTO>> findAll() {
		List<OrdemServicoDTO> list = ordemServicoService.findAll().stream().map(obj -> new OrdemServicoDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(list);
	}

	// Inserir registro
	@PostMapping
	public ResponseEntity<OrdemServicoDTO> create(@Valid @RequestBody OrdemServicoDTO obj) {
		obj = new OrdemServicoDTO(ordemServicoService.create(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	// Modificar registro
	@PutMapping
	public ResponseEntity<OrdemServicoDTO> update(@RequestBody OrdemServicoDTO obj) {
		obj = new OrdemServicoDTO(ordemServicoService.update(obj));

		return ResponseEntity.ok().body(obj);
	}
}
