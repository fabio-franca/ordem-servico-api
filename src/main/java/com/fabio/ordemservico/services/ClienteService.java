package com.fabio.ordemservico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabio.ordemservico.domain.Cliente;
import com.fabio.ordemservico.domain.Pessoa;
import com.fabio.ordemservico.dtos.ClienteDTO;
import com.fabio.ordemservico.repositories.ClienteRepository;
import com.fabio.ordemservico.repositories.PessoaRepository;
import com.fabio.ordemservico.services.exceptions.DataIntegratyViolationException;
import com.fabio.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	// Encontrar pelo ID
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id); // Optional porque pode encontrar o id ou não...
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	// Listar todos
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	// Salvar técnico
	public Cliente create(ClienteDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}

		Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return clienteRepository.save(newObj);
	}

	// Modificar técnico
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		
		return clienteRepository.save(oldObj);
	}
	
	// Excluir técnico
	public void delete(Integer id) {
		Cliente obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui ordens de serviço. Não pode ser excluído!"); 
		}
		
		clienteRepository.deleteById(id);
	}
	
	
	//BuscarPeloCPF
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

	

}
