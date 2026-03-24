package com.nexus.service;

import com.nexus.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
// import java.util.stream.*;
import java.util.stream.Collectors;

/**
 * Atua como o contêiner principal do sistema Nexus. Armazena a lista global de tarefas 
 * e projetos, oferecendo métodos de busca, filtragem e análises executivas.
 */
public class Workspace {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();

    /**
     * Adiciona uma nova tarefa à lista global do workspace.
     *
     * @param task A tarefa a ser adicionada.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Adiciona um novo projeto à lista global do workspace.
     *
     * @param project O projeto a ser adicionado.
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Retorna a lista de tarefas contidas no workspace.
     * Utiliza {@link Collections#unmodifiableList} para garantir o encapsulamento e prevenir 
     * modificações diretas na lista interna.
     *
     * @return Uma visão não modificável da lista de tarefas.
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Retorna a lista de projetos contidos no workspace.
     * Utiliza {@link Collections#unmodifiableList} para garantir o encapsulamento e prevenir 
     * modificações diretas na lista interna.
     *
     * @return Uma visão não modificável da lista de projetos.
     */
    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    /**
     * Identifica os usuários com maior produtividade no sistema.
     * * @return Uma lista com os 3 usuários que possuem o maior número absoluto de tarefas concluídas (DONE).
     */
    public List<User> TopPerformers() {
        List<User> ul = getTasks().stream().filter(m -> m.getStatus().equals(TaskStatus.DONE))
        .collect(Collectors.groupingBy(Task::getOwner, Collectors.counting())).entrySet().stream()
        .sorted((n,m) -> m.getValue().compareTo(n.getValue())).limit(3).map(j -> j.getKey()).toList();
        return ul;
    }

    /**
     * Localiza os usuários que estão com carga de trabalho acima do limite saudável.
     * * @return Uma lista de usuários cuja carga atual de tarefas IN_PROGRESS ultrapassa 10.
     */
    public List<User> OverloadedUsers() {
        List<User> ul = getTasks().stream().filter(m -> m.getStatus().equals(TaskStatus.IN_PROGRESS))
        .collect(Collectors.groupingBy(Task::getOwner, Collectors.counting())).entrySet().stream()
        .filter(j -> j.getValue() > 10).map(j -> j.getKey()).toList();
        return ul;
    }

    /**
     * Analisa a distribuição de status para encontrar os principais bloqueios no fluxo de trabalho.
     * * @return O tipo de status (exceto DONE) que possui o maior número de tarefas associadas.
     */
    public Optional<TaskStatus> GlobalBottlenecks() {
        Optional<TaskStatus> ts = getTasks().stream().filter(m -> !m.getStatus().equals(TaskStatus.DONE))
        .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting())).entrySet().stream()
        .sorted((n,m) -> m.getValue().compareTo(n.getValue())).map(j -> j.getKey()).findFirst();
        return ts;
    }

    /**
     * Gera e imprime relatórios analíticos utilizando os métodos de processamento de fluxo (Streams).
     */
    public void report_Status() {
        System.out.println("Relatorio Geral Rapido:");
        System.out.println("Os tres usuarios com maior numero de tarefas marcada como concluidas são:");
        TopPerformers().stream().forEach(System.out::println);
        System.out.println("Todos os usuarios cuja carga de trabalho atual ultrapassam 10 tarefas sao:");
        OverloadedUsers().stream().forEach(System.out::println);
        System.out.println("O status com maior numero de tarefas no sistema, sem contar com o DONE e:" + GlobalBottlenecks());
    }
}