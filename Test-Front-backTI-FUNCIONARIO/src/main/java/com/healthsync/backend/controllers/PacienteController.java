package com.healthsync.backend.controllers;

import com.healthsync.backend.controllers.dto.CreateDtoPaciente;
import com.healthsync.backend.controllers.dto.UpdateDto;
import com.healthsync.backend.models.Funcionario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.healthsync.backend.models.Paciente;
import com.healthsync.backend.services.PacienteService;

import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController
public class PacienteController{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  PacienteService pacienteService;


    // Método para obter todos os pacientes
    @GetMapping(value = "/paciente")
    public List<Paciente> obterTodos(){
        return this.pacienteService.obterTodos();
    }

    // Método para obter um paciente por id
    @GetMapping(value = "/paciente/id/{id}")
    public ResponseEntity<Paciente> obterPorId(@PathVariable Long id){
        Paciente paciente = this.pacienteService.obterPorId(id);
        return ResponseEntity.ok().body(paciente);
    }

    // Método para obter um paciente por cpf
    @GetMapping(value = "/paciente/cpf/{cpf}")
    public ResponseEntity<Paciente> obterPorCpf(@PathVariable String cpf){
        Paciente paciente = this.pacienteService.findByCpf(cpf);
        return ResponseEntity.ok().body(paciente);
    }

    // Método para criar um paciente
    @Transactional
    @PostMapping(value = "/paciente")
    public ResponseEntity<Void> createPaciente(@RequestBody CreateDtoPaciente dto){

    var paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());
        paciente.setCidade(dto.cidade());
        paciente.setTelefone(dto.telefone());
        paciente.setCep(dto.cep());
        paciente.setComplemento(dto.complemento());
        paciente.setNumero(dto.numero());
        paciente.setPassword(passwordEncoder.encode(dto.password()));

        this.pacienteService.criar(paciente);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // Método para atualizar um paciente pelo cpff
    @PutMapping(value = "/paciente/cpf/{cpf}")
    public ResponseEntity<Void> atualizar(@PathVariable String cpf,
                                          @RequestBody UpdateDto updateDto){
        pacienteService.updateUserByCpf(cpf, updateDto);
        return ResponseEntity.noContent().build();
    }


    // Método para excluir um paciente pelo CPF
    @DeleteMapping(value = "/paciente/cpf/{cpf}")
    public ResponseEntity<Void> excluir(@PathVariable String cpf) {
        pacienteService.excluir(cpf);  // Usando o CPF do paciente para excluir
        return ResponseEntity.noContent().build();  // Retorna o código 204 (No Content)
    }

}
