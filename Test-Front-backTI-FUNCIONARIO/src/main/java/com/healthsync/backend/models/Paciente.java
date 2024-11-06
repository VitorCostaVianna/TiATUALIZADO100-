package com.healthsync.backend.models;

import com.healthsync.backend.controllers.dto.LoginRequest;
import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "pac_cpf", unique = true)
    private String cpf;

    @Column(name = "pac_nome")
    private String nome;

    @Column(name = "pac_telefone")
    private String telefone;

    @Column(name = "pac_email", unique = true)
    private String email;

    @Column(name = "pac_cep")
    private String cep;

    @Column(name = "pac_rua")
    private String rua;

    @Column(name= "numero")
    private Integer numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "password", nullable = false)
    private String password;


    public Paciente(){

    }

    public Paciente(String cpf, String email, String nome, String cep, String rua, Integer numero, String complemento, String cidade, String teleone, String password) {
        this.setCpf(cpf);
        this.setEmail(email);
        this.setNome(nome);
        this.setCep(cep);;
        this.setRua(rua);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setCidade(cidade);
        this.setTelefone(teleone);
        this.setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
   
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (isTelefoneValid(telefone)) {
            this.telefone = telefone;
        } else {
            throw new IllegalArgumentException("Telefone inválido");
        }
    }

    private boolean isTelefoneValid(String telefone) {
        return telefone.length() >= 9 && telefone.length() <= 11;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isEmailValid(email)){
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(id, paciente.id) && Objects.equals(cpf, paciente.cpf) && Objects.equals(nome, paciente.nome) && Objects.equals(email, paciente.email) && Objects.equals(cep, paciente.cep) && Objects.equals(rua, paciente.rua) && Objects.equals(numero, paciente.numero) && Objects.equals(complemento, paciente.complemento) && Objects.equals(cidade, paciente.cidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, nome, email, cep, rua, numero, complemento, cidade, telefone);
    }

}
