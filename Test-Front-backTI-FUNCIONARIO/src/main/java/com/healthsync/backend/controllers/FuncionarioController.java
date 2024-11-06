package com.healthsync.backend.controllers;


import com.healthsync.backend.controllers.dto.CreatDtoFuncionario;
import com.healthsync.backend.controllers.dto.UpdateDtoFuncionario;
import com.healthsync.backend.models.Funcionario;
import com.healthsync.backend.models.Role;
import com.healthsync.backend.repositories.RoleRepository;
import com.healthsync.backend.services.FuncionarioService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Set;

@RestController
public class FuncionarioController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final FuncionarioService funcionarioService;

    public FuncionarioController(BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, FuncionarioService funcionarioService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionario")
    public ResponseEntity<List<Funcionario>> listFuncionarios(){
        var funcionarios = this.funcionarioService.obterTodos();
        return ResponseEntity.ok().body(funcionarios);
    }

    @GetMapping(value = "/funcionario/id/{id}")
    public ResponseEntity<Funcionario> obterPorId(@PathVariable Long id){
        Funcionario funcionario = this.funcionarioService.findById(id);
        return ResponseEntity.ok().body(funcionario);
    }

    @GetMapping(value = "/funcionario/cpf/{cpf}")
    public ResponseEntity<Funcionario> obterPorCpf(@PathVariable String cpf){
        Funcionario funcionario = this.funcionarioService.findByCpf(cpf);
        return ResponseEntity.ok().body(funcionario);
    }

    @Transactional
    @PostMapping("/funcionario")
    public ResponseEntity<Void> createFuncionario(@RequestBody CreatDtoFuncionario dto) {

        // Tenta encontrar o papel BASIC; se não for encontrado, trate o erro
        var basicRole = this.roleRepository.findByName(Role.Values.BASIC.name());

        if (basicRole == null) {
            throw new IllegalStateException("Papel BASIC não encontrado no banco de dados.");
        }

        // Prossegue para criar o novo Funcionario
        var funcionario = new Funcionario();
        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        funcionario.setEmail(dto.email());
        funcionario.setCidade(dto.cidade());
        funcionario.setCargo(dto.cargo());
        funcionario.setEspecialidade(dto.especialidade());
        funcionario.setTelefone(dto.telefone());
        funcionario.setSalario(dto.salario());
        funcionario.setPassword(passwordEncoder.encode(dto.password()));
        funcionario.setRoles(Set.of(basicRole));

        funcionarioService.criar(funcionario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/funcionario/cpf/{cpf}")
    public ResponseEntity<Void> atualizar(@PathVariable String cpf,
                                          @RequestBody UpdateDtoFuncionario dtoFuncionario){
        funcionarioService.updateUserByCpf(cpf, dtoFuncionario);
        return ResponseEntity.noContent().build();
    }



    @DeleteMapping(value = "/funcionario/cpf/{cpf}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable String cpf) {
        this.funcionarioService.delete(cpf);  // Usando o CPF do paciente para excluir
        return ResponseEntity.noContent().build();  // Retorna o código 204 (No Content)
    }


}
