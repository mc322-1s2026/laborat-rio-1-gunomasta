package com.nexus.service;

import com.nexus.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
// import java.util.stream.*;
import java.util.stream.Collectors;

public class Workspace {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Task> getTasks() {
        // Retorna uma visão não modificável para garantir encapsulamento
        return Collections.unmodifiableList(tasks);
    }

    public List<Project> getProjects() {
        // Retorna uma visão não modificável para garantir encapsulamento
        return Collections.unmodifiableList(projects);
    }

    public List<User> TopPerformers() {
        // retorna os 3 usuarios que possuem maior numero absoluto de tarefas DONE
        List<User> ul = getTasks().stream().filter(m -> m.getStatus().equals(TaskStatus.DONE))
        .collect(Collectors.groupingBy(Task::getOwner, Collectors.counting())).entrySet().stream()
        .sorted((n,m) -> m.getValue().compareTo(n.getValue())).limit(3).map(j -> j.getKey()).toList();
        return ul;
    }

    public List<User> OverloadedUsers() {
        // retorna uma lista com usuarios com mais de 10 tarefas in progress
        List<User> ul = getTasks().stream().filter(m -> m.getStatus().equals(TaskStatus.IN_PROGRESS))
        .collect(Collectors.groupingBy(Task::getOwner, Collectors.counting())).entrySet().stream()
        .filter(j -> j.getValue() > 10).map(j -> j.getKey()).toList();
        return ul;
    }

    public Optional<TaskStatus> GlobalBottlenecks() {
        // retorna o tipo de status com maior numero absoluto de tarefas sem ser o DONE
        Optional<TaskStatus> ts = getTasks().stream().filter(m -> !m.getStatus().equals(TaskStatus.DONE))
        .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting())).entrySet().stream()
        .sorted((n,m) -> m.getValue().compareTo(n.getValue())).map(j -> j.getKey()).findFirst();
        return ts;
    }

    public void report_Status() {
        // gera relatorios usando a Stream API
        System.out.println("Relatorio Geral Rapido:");
        System.out.println("Os tres usuarios com maior numero de tarefas marcada como concluidas são:");
        TopPerformers().stream().forEach(System.out::println);
        System.out.println("Todos os usuarios cuja carga de trabalho atual ultrapassam 10 tarefas sao:");
        OverloadedUsers().stream().forEach(System.out::println);
        System.out.println("O status com maior numero de tarefas no sistema, sem contar com o DONE e:" + GlobalBottlenecks());
    }
}