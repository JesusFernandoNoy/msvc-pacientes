package org.healthcare.springcloud.msvc.pacientes.services;

import java.util.List;
import java.util.Optional;

import org.healthcare.springcloud.msvc.pacientes.models.entity.Paciente;


public interface IPacienteService {
	
	List<Paciente> findAll();
    Optional<Paciente> findById(Long id);
    Paciente save(Paciente paciente);
    void deleteById(Long id);
    List<Paciente> findByIds(Iterable<Long> ids);    
    Optional<Paciente> findByNumeroDocumento(int document);
    

}
