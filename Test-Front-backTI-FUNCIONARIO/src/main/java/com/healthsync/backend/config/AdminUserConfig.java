package com.healthsync.backend.config;

import com.healthsync.backend.models.Role;
import com.healthsync.backend.models.Funcionario;
import com.healthsync.backend.repositories.FuncionarioRepository;
import com.healthsync.backend.repositories.PacienteRepository;
import com.healthsync.backend.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private FuncionarioRepository funcionarioRepository;

    public AdminUserConfig(RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           FuncionarioRepository funcionarioRepository, PacienteRepository pacienteRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verifica se o papel ADMIN existe; se não, cria e salva
        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName(Role.Values.ADMIN.name());
            roleRepository.save(roleAdmin);
        }

        // Procura um usuário admin
        var userAdmin = funcionarioRepository.findByNome("admin");

        Role finalRoleAdmin = roleAdmin;
        userAdmin.ifPresentOrElse(
                funcionario -> System.out.println("admin já existe"),
                () -> {
                    var funcionario = new Funcionario();
                    funcionario.setNome("admin");
                    funcionario.setPassword(passwordEncoder.encode("123"));
                    funcionario.setEmail("admin@admin.com");

                    // Associa o papel ADMIN ao novo usuário admin
                    funcionario.setRoles(Set.of(finalRoleAdmin));
                    funcionarioRepository.save(funcionario);
                    System.out.println("Usuário admin criado com sucesso.");
                }
        );
    }


}
