package com.healthsync.backend.controllers.dto;

public record CreateDtoPaciente (String nome,String cpf ,String email
                                ,String cep, String rua,
                                 String cidade, String complemento,
                                 String telefone, Integer numero , String password) {
}
