package org.healthcare.springcloud.msvc.pacientes.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.healthcare.springcloud.msvc.pacientes.models.entity.Paciente;
import org.healthcare.springcloud.msvc.pacientes.services.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacienteController {
	
	 @Autowired
	 private IPacienteService service;
	 

    @GetMapping
    public List<Paciente> listar(){
        return service.findAll();
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Paciente> PacienteOptional = service.findById(id);

        if(PacienteOptional.isPresent()){
            return ResponseEntity.ok(PacienteOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Paciente paciente, BindingResult result){

        if(result.hasErrors()){
            return validar(result);
        }
        if(!paciente.getEmail().isEmpty() && service.findByNumeroDocumento(paciente.getNumeroDocumento()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje","Ya existe un Paciente con ese numero de documento!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(paciente));
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Paciente paciente, BindingResult result, @PathVariable Long id){

        if(result.hasErrors()){
            return validar(result);
        }

        Optional<Paciente> o = service.findById(id);

        if (o.isPresent()){
            Paciente pacienteOb = o.get();

            if(!paciente.getEmail().isEmpty() &&
                    paciente.getNumeroDocumento() != pacienteOb.getNumeroDocumento() &&
                    service.findByNumeroDocumento(paciente.getNumeroDocumento()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje","Ya existe un Paciente con ese número documento!"));
            }

            pacienteOb.setNombre(paciente.getNombre());
            pacienteOb.setApellidos(paciente.getApellidos());
            pacienteOb.setFechaNacimiento(paciente.getFechaNacimiento());
            pacienteOb.setGenero(paciente.getGenero());
            pacienteOb.setTipoDocumento(paciente.getTipoDocumento());
            pacienteOb.setNumeroDocumento(paciente.getNumeroDocumento());            
            pacienteOb.setEmail(paciente.getEmail());          
            
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pacienteOb));
        }
        return ResponseEntity.notFound().build();
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){

        Optional<Paciente> o = service.findById(id);
        if(o.isPresent()){
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }
    
    
    @GetMapping("/pacientes-por-lista")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.findByIds(ids));
    }        
    
    
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }	 
	 
}
