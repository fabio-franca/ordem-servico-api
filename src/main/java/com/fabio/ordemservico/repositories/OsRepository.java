package com.fabio.ordemservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabio.ordemservico.domain.OS;

@Repository
public interface OsRepository extends JpaRepository<OS, Integer>{

}
