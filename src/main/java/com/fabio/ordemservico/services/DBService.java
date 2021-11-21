package com.fabio.ordemservico.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabio.ordemservico.domain.Cliente;
import com.fabio.ordemservico.domain.OS;
import com.fabio.ordemservico.domain.Tecnico;
import com.fabio.ordemservico.domain.enums.Prioridade;
import com.fabio.ordemservico.domain.enums.Status;
import com.fabio.ordemservico.repositories.ClienteRepository;
import com.fabio.ordemservico.repositories.OsRepository;
import com.fabio.ordemservico.repositories.TecnicoRepository;

@Service
public class DBService {

	// Injeção de dependencias
	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private OsRepository osRepository;

	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "João melão", "794.152.240-37", "(85) 98676-6060");
		Cliente c1 = new Cliente(null, "Dorothy Maria", "126.033.870-30", "(88) 98888-0000");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));

		osRepository.saveAll(Arrays.asList(os1));
	}
}
