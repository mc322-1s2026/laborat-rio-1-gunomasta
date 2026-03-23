package com.nexus.model;
import java.util.List;
import com.nexus.exception.NexusValidationException;
import java.util.stream.*;

public class Project{
    private final String projectName;
    private final int budgetHours;
    private final List<Task> taskList;

    public Project(String projectName, int budgetHours) {   
        if (projectName == null || projectName.isBlank()) {
            throw new IllegalArgumentException("Nome do projeto não pode ser vazio.");
        }
        if (budgetHours <= 0){
            throw new IllegalArgumentException("A budget nao pode ser menor ou igual a 0");
        }
        this.projectName = projectName;
        this.budgetHours = budgetHours;
        this.taskList = new ArrayList<Task>();
    }

    public String consultProjectName() {
        return projectName;
    }

    public String consultBudgetHours() {
        return budgetHours;
    }

    public List<Task> consultTaskList() {
        return taskList;
    }

    public int getTotalHours() {
        List<Task> tasks = consultTaskList();
        int aux = tasks.streams().mapToInt(m -> m.estimatedEffort).sum();
        return aux;
    }

    public float getConclusionPercentage() {
        List<Task> tasks = consultTaskList();
        int totalTasks = tasks.size();
        int doneTasks = tasks.streams().filter(m -> m.status.equals(TaskStatus.DONE).sum());
        return doneTasks/totalTasks;
    }

    public addTask(Task t){
        if (t.estimatedEffort <= 0 ||(this.getTotalHours() + t.estimatedEffort > this.budgetHours)) {
            throw new NexusValidationException("Nao fora possivel adicionar essa tarefa no projeto designado");
        } else {
            this.taskList.add(t);
        }
    }   
}