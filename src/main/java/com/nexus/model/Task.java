package com.nexus.model;
import java.time.LocalDate;
import com.nexus.exception.NexusValidationException;

/**
 * O coração do sistema Nexus. Representa uma atividade e opera como uma máquina de estados finitos 
 * (TO_DO -> IN_PROGRESS -> DONE).
 */
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
    private Project project;

    /**
     * Constrói uma nova tarefa. O ID e o deadline (prazo) definidos no nascimento são imutáveis 
     * e protegidos contra alterações posteriores.
     *
     * @param title O título descritivo da tarefa.
     * @param deadline O prazo limite da tarefa.
     * @param estimatedEffort O esforço estimado em horas.
     * @param project O projeto ao qual esta tarefa pertence.
     */
    public Task(String title, LocalDate deadline, int estimatedEffort, Project project) {
        this.id = nextId++;
        this.deadline = deadline;
        this.title = title;
        this.status = TaskStatus.TO_DO;
        this.estimatedEffort = estimatedEffort;
        this.project = project;
        
        // Ação do Aluno:
        totalTasksCreated++; 
    }

    /**
     * Move a tarefa para o status IN_PROGRESS.
     * Só é permitido alterar para este estado se houver um usuário (owner) atribuído e se a tarefa 
     * não estiver no estado BLOCKED.
     * * @throws NexusValidationException Se não houver dono atribuído ou se a tarefa estiver bloqueada.
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
     * Finaliza a tarefa movendo-a para o status DONE.
     * A transição só é permitida se a tarefa não estiver atualmente no status BLOCKED.
     * * @throws NexusValidationException Se a tarefa estiver com status BLOCKED.
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

    /**
     * Bloqueia ou desbloqueia a tarefa.
     * Uma tarefa pode ser movida para BLOCKED a partir de qualquer estado, exceto se já estiver concluída (DONE).
     *
     * @param blocked true para bloquear a tarefa, false para retornar ao status padrão.
     */
    public void markAsBlocked(boolean blocked) {
        if (blocked && !this.status.equals(TaskStatus.DONE)) {
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
    public Project getProject() {return project; }
    // public Project getProject() {return project; }

    /**
     * Atribui um usuário como responsável pela tarefa.
     * * @param user O usuário que será o dono (owner) da tarefa.
     */
    public void setOwner(User user) {
        this.owner = user;
    }
}