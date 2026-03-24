package com.nexus.model;
import com.nexus.exception.NexusValidationException;
import com.nexus.model.User;
import java.util.List;
import com.nexus.service.Workspace;

/**
 * Representa o agente executor no sistema Nexus, possuindo informações de identidade 
 * e operando como responsável pelas tarefas.
 */
public class User {
    private final String username;
    private final String email;

    /**
     * Construtor que garante a integridade da identidade do usuário validando regras de negócio estritas.
     *
     * @param username O nome de usuário. Não pode ser nulo ou vazio.
     * @param email O e-mail do usuário. Deve conter '@' e seguir o formato padrão.
     * @throws NexusValidationException Se as validações de username ou email falharem.
     */
    public User(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new NexusValidationException("Username não pode ser vazio.");
        }
        if (!email.matches(".+@.+.com")) {
            throw new NexusValidationException("O email não segue a formatação desejada.");        
        }
        this.username = username;
        this.email = email.toLowerCase();
    }

    public String consultEmail() { return email; }
    public String consultUsername() { return username; }

    /**
     * Filtra dinamicamente a lista de tarefas globais para calcular a carga de trabalho atual do usuário.
     * Conta apenas as tarefas que estão em andamento (IN_PROGRESS) e que pertencem a este usuário.
     *
     * @param w O workspace contendo a lista de tarefas.
     * @return O número de tarefas ativas sob responsabilidade do usuário.
     */
    public int calculateWorkload(Workspace w) {
        List<Task> tasks = w.getTasks();
        int res = (int) tasks.stream().filter(m -> m.getStatus().equals(TaskStatus.IN_PROGRESS) && m.getOwner().equals(this)).count();
        return res;
    }
}

