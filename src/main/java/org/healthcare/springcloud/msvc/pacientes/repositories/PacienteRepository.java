package org.healthcare.springcloud.msvc.pacientes.repositories;

import java.util.Optional;


import org.healthcare.springcloud.msvc.pacientes.models.entity.Paciente;
import org.springframework.data.repository.CrudRepository;

public interface PacienteRepository extends CrudRepository<Paciente,Long> {
	
	 Optional<Paciente> findByNumeroDocumento(int document);

}
