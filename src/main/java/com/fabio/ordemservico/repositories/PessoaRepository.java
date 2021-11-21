package com.fabio.ordemservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fabio.ordemservico.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
	
	@Query("SELECT obj FROM Pessoa obj WHERE obj.cpf =:cpf")  //ter cuidado com os espaços em branco! Como exemplo em =:cpf deve ser junto.
	Pessoa findByCPF(@Param("cpf") String cpf);
}
