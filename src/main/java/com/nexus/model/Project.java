package com.nexus.model;
import java.util.List;
import com.nexus.exception.NexusValidationException;
import java.util.ArrayList;

/**
 * Representa um portfólio de projeto no sistema, agrupando tarefas e gerenciando o orçamento 
 * de horas (budget).
 */
public class Project{
    private final String projectName;
    private final int budgetHours;
    private final List<Task> taskList;

    /**
     * Inicializa um novo projeto validando suas informações básicas.
     *
     * @param projectName O nome do projeto. Não pode ser nulo ou vazio.
     * @param budgetHours O orçamento total de horas do projeto. Deve ser maior que zero.
     * @throws IllegalArgumentException Se o nome for inválido ou as horas forem menores ou iguais a zero.
     */
    public Project(String projectName, int budgetHours) {   
        if (projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("Nome do projeto não pode ser vazio.");
        }
        if (budgetHours <= 0){
            throw new IllegalArgumentException("A budget não pode ser menor ou igual a 0");
        }
        this.projectName = projectName;
        this.budgetHours = budgetHours;
        this.taskList = new ArrayList<Task>();
    }


    public String consultProjectName() { return projectName; }
    public int consultBudgetHours() { return budgetHours; }
    public List<Task> consultTaskList() { return taskList; }

    /**
     * Calcula o esforço total estimado de todas as tarefas atualmente vinculadas ao projeto.
     *
     * @return O total de horas estimadas cadastradas no projeto.
     */
    public int getTotalHours() {
        List<Task> tasks = consultTaskList();
        int aux = tasks.stream().mapToInt(m -> m.getestimatedEffort()).sum();
        return aux;
    }

    /**
     * Adiciona uma nova tarefa ao projeto validando o orçamento.
     * A soma das horas das tarefas atuais com a nova tarefa não pode exceder o totalBudget do projeto.
     *
     * @param t A tarefa a ser adicionada.
     * @throws NexusValidationException Se o esforço for inválido ou estourar o orçamento do projeto.
     */
    public void addTask(Task t){
        if (t.getestimatedEffort() <= 0 ||(this.getTotalHours() + t.getestimatedEffort() > this.budgetHours)) {
            throw new NexusValidationException("Nao fora possivel adicionar essa tarefa no projeto designado");
        } else {
            this.taskList.add(t);
        }
    }

    /**
     * Calcula o percentual de conclusão do projeto verificando a saúde atual.
     *
     * @return A razão entre as tarefas concluídas (DONE) e o total de tarefas.
     */
    public float ProjectHealth() {
        List<Task> tasks = consultTaskList();
        int totalTasks = tasks.size();
        int doneTasks = (int) tasks.stream().filter(m -> m.getStatus().equals(TaskStatus.DONE)).count();
        return doneTasks/totalTasks;
    }
}