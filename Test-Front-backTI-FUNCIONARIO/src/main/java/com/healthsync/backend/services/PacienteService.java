package com.healthsync.backend.services;

import com.healthsync.backend.controllers.dto.UpdateDto;
import com.healthsync.backend.exceptions.ResourceNotFoundException;
import com.healthsync.backend.models.Paciente;
import com.healthsync.backend.repositories.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> obterTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente obterPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));
    }

    public Paciente findByCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com CPF: " + cpf));
    }

    public Paciente findByEmail(String email){
        return this.pacienteRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado"));
    }

    @Transactional
    public Paciente criar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void updateUserByCpf(String userCpf, UpdateDto updateDto) {

        Paciente user = pacienteRepository.findByCpf(userCpf).orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com CPF: " + userCpf));

        if (updateDto.nome() != null) {
            user.setNome(updateDto.nome());
        }
        if (updateDto.email() != null) {
            user.setEmail(updateDto.email());
        }
        if (updateDto.cep() != null) {
            user.setCep(updateDto.cep());
        }
        if (updateDto.rua() != null) {
            user.setRua(updateDto.rua());
        }
        if (updateDto.cidade() != null) {
            user.setCidade(updateDto.cidade());
        }
        if (updateDto.complemento() != null) {
            user.setComplemento(updateDto.complemento());
        }
        if (updateDto.telefone() != null) {
            user.setTelefone(updateDto.telefone());
        }
        if (updateDto.numero() != null) {
            user.setNumero(updateDto.numero());
        }


        pacienteRepository.save(user);
    }

    public void excluir(String cpf) {
        Paciente paciente = findByCpf(cpf);
        pacienteRepository.deleteById(paciente.getId());
    }
}
