package com.fabio.ordemservico.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabio.ordemservico.domain.Cliente;
import com.fabio.ordemservico.domain.OS;
import com.fabio.ordemservico.domain.Tecnico;
import com.fabio.ordemservico.domain.enums.Prioridade;
import com.fabio.ordemservico.domain.enums.Status;
import com.fabio.ordemservico.dtos.OrdemServicoDTO;
import com.fabio.ordemservico.repositories.OsRepository;
import com.fabio.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemServicoService {

	// Injeções

	@Autowired
	private OsRepository osRepository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	// Métodos

	public OS findById(Integer id) {
		Optional<OS> obj = osRepository.findById(id);

		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", tipo" + OS.class.getName()));
	}

	public List<OS> findAll() {
		return osRepository.findAll();
	}

	public OS create(@Valid OrdemServicoDTO obj) {
		return fromDTO(obj);
	}

	private OS fromDTO(OrdemServicoDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));

		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		newObj.setTecnico(tec);
		Cliente cli = clienteService.findById(obj.getCliente());
		newObj.setCliente(cli);
		
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}

		return osRepository.save(newObj);

	}

	public OS update(OrdemServicoDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}

}
