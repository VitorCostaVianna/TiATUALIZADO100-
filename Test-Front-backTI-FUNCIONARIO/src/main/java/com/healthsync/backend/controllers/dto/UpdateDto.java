package com.healthsync.backend.controllers.dto;

public record UpdateDto(String nome, String email, String cep, String rua, String cidade, String complemento , String telefone, Integer numero) {
}
