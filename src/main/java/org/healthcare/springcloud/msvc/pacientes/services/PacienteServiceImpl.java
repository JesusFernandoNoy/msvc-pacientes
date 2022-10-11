package org.healthcare.springcloud.msvc.pacientes.services;

import java.util.List;
import java.util.Optional;

import org.healthcare.springcloud.msvc.pacientes.models.entity.Paciente;
import org.healthcare.springcloud.msvc.pacientes.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteServiceImpl implements IPacienteService {

	@Autowired
	PacienteRepository repository;
		
	@Override
	@Transactional(readOnly = true)
	public List<Paciente> findAll() {
		return (List<Paciente>) repository.findAll();		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Paciente> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Paciente save(Paciente paciente) {
		return repository.save(paciente);

	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
		// pendiente eliminar relacion del programa dode se encuentra el paciente
	}

	@Override
	@Transactional(readOnly = true)
	public List<Paciente> findByIds(Iterable<Long> ids) {
		return (List<Paciente>) repository.findAllById(ids);
	}

	@Override
	public Optional<Paciente> findByNumeroDocumento(int document) {		
		return repository.findByNumeroDocumento(document);
	}

}
