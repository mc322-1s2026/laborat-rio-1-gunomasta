package com.nexus.model;
import java.time.LocalDate;
import com.nexus.exception.NexusValidationException;

public class Task {
    // Métricas Globais (Alunos implementam a lógica de incremento/decremento)
    public static int totalTasksCreated = 0;
    public static int activeWorkload = 0;

    private static int nextId = 1;

    private final int id;
    private final LocalDate deadline; // Imutável após o nascimento
    private String title;
    private TaskStatus status;
    private User owner;
    private int estimatedEffort;

    public Task(String title, LocalDate deadline, int estimatedEffort) {
        this.id = nextId++;
        this.deadline = deadline;
        this.title = title;
        this.status = TaskStatus.TO_DO;
        this.estimatedEffort = estimatedEffort;
        
        // Ação do Aluno:
        totalTasksCreated++; 
    }

    /**
     * Move a tarefa para IN_PROGRESS.
     * Regra: Só é possível se houver um owner atribuído e não estiver BLOCKED.
     */
    public void markAsInProgress() {
        // TODO: Implementar lógica de proteção e atualizar activeWorkload
        if (getOwner() == null || getStatus().equals(TaskStatus.BLOCKED)){
            throw new NexusValidationException("Não é possível marcar essa tarefa como IN_PROGRESS.");
        } else {
            this.status = TaskStatus.IN_PROGRESS;
            activeWorkload++;
        }
    }

    /**
     * Finaliza a tarefa.
     * Regra: Só pode ser movida para DONE se não estiver BLOCKED.
     */
    public void markAsDone() {
        // TODO: Implementar lógica de proteção e atualizar activeWorkload (decrementar)
        if (getOwner() == null || getStatus().equals(TaskStatus.BLOCKED)){
            throw new NexusValidationException("Nao pode mexer em uma tarefa bloqueada");
        } else {
            this.status = TaskStatus.DONE;
            activeWorkload--;
        }
    }

    // Se for adicionar uma task recem criada cria com 0, mudar status seta como 1
    public void markAsBlocked(boolean blocked) {
        if (blocked && !this.status.equals(TaskStatus.DONE) && getOwner() != null) {
            this.status = TaskStatus.BLOCKED;
        } else {
            this.status = TaskStatus.TO_DO; // Simplificação para o Lab
        }
    }

    // Getters
    public int getId() { return id; }
    public TaskStatus getStatus() { return status; }
    public String getTitle() { return title; }
    public LocalDate getDeadline() { return deadline; }
    public User getOwner() { return owner; }
    public int getestimatedEffort() {return estimatedEffort; }
    // public Project getProject() {return project; }

    public void setOwner(User user) {
        this.owner = user;
    }
}