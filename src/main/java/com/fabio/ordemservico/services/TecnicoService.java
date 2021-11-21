package com.fabio.ordemservico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabio.ordemservico.domain.Pessoa;
import com.fabio.ordemservico.domain.Tecnico;
import com.fabio.ordemservico.dtos.TecnicoDTO;
import com.fabio.ordemservico.repositories.PessoaRepository;
import com.fabio.ordemservico.repositories.TecnicoRepository;
import com.fabio.ordemservico.services.exceptions.DataIntegratyViolationException;
import com.fabio.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	// Encontrar pelo ID
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id); // Optional porque pode encontrar o id ou não...
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	// Listar todos
	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	// Salvar técnico
	public Tecnico create(TecnicoDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}

		Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return tecnicoRepository.save(newObj);
	}

	// Modificar técnico
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		
		return tecnicoRepository.save(oldObj);
	}
	
	// Excluir técnico
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui ordens de serviço. Não pode ser excluído!"); 
		}
		
		tecnicoRepository.deleteById(id);
	}
	
	
	//BuscarPeloCPF
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

	

}
