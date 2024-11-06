package com.healthsync.backend.controllers.dto;

public record UpdateDtoFuncionario(String nome, String email ,String telefone , Double salario, String cargo, String cidade, String especialidade, String password) {
}
