package com.healthsync.backend.models;

import com.healthsync.backend.controllers.dto.LoginRequest;
import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fun_id")
    private  Long id;

    @Column(name = "fun_cpf", unique = true)
    private String cpf;

    @Column(name = "fun_password", nullable = false)
    private String password;

    @Column(name = "fun_nome")
    private String nome;

    @Column(name = "fun_telefone")
    private String telefone;

    @Column(name = "fun_email", unique = true)
    private String email;

    @Column(name = "fun_salario")
    private Double salario;

    @Column(name = "fun_cargo")
    private Cargo cargo;

    @Column(name = "fun_cidade")
    private String cidade;

    @Column(name = "especialidade")
    private String especialidade;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "funcionario_roles",
            joinColumns = @JoinColumn(name = "fun_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Funcionario(){

    }

    public Funcionario(String cpf, String email, String nome, String telefone, Double salario, String cargo, String cidade, String especialidade , String password) {
        this.setCpf(cpf);
        this.setEmail(email);
        this.setNome(nome);
        this.setTelefone(telefone);
        this.setSalario(salario);
        this.setCargo(cargo);
        this.setCidade(cidade);
        this.setEspecialidade(especialidade);
        this.setPassword(password);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = Cargo.parse(cargo);
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
    // Permite que a especialidade seja nula ou uma string vazia
    this.especialidade = especialidade; // Atribui a especialidade

    // Se o funcionário não for médico, também garante que a especialidade não seja obrigatória
    if (this.cargo != Cargo.MEDICO) {
        this.especialidade = null; // Limpa a especialidade se não for médico
    }
    }
    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Funcionario other = (Funcionario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        return true;
    }

}