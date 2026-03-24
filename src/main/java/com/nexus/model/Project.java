package com.nexus.model;
import java.util.List;
import com.nexus.exception.NexusValidationException;
import java.util.ArrayList;

public class Project{
    private final String projectName;
    private final int budgetHours;
    private final List<Task> taskList;

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

    public String consultProjectName() {
        return projectName;
    }

    public int consultBudgetHours() {
        return budgetHours;
    }

    public List<Task> consultTaskList() {
        return taskList;
    }

    public int getTotalHours() {
        List<Task> tasks = consultTaskList();
        int aux = tasks.stream().mapToInt(m -> m.getestimatedEffort()).sum();
        return aux;
    }

    public void addTask(Task t){
        if (t.getestimatedEffort() <= 0 ||(this.getTotalHours() + t.getestimatedEffort() > this.budgetHours)) {
            throw new NexusValidationException("Nao fora possivel adicionar essa tarefa no projeto designado");
        } else {
            this.taskList.add(t);
        }
    }

    public float ProjectHealth() {
        List<Task> tasks = consultTaskList();
        int totalTasks = tasks.size();
        int doneTasks = (int) tasks.stream().filter(m -> m.getStatus().equals(TaskStatus.DONE)).count();
        return doneTasks/totalTasks;
    }
}