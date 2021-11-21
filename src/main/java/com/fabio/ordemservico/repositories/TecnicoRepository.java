package com.fabio.ordemservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fabio.ordemservico.domain.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

	@Query("SELECT obj FROM Tecnico obj WHERE obj.cpf =:cpf")  //ter cuidado com os espaços em branco! Como exemplo em =:cpf deve ser junto.
	Tecnico findByCPF(@Param("cpf") String cpf);

}
