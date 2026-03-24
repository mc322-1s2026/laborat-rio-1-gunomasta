package com.nexus.model;

/**
 * Representa os estados possíveis de uma tarefa no motor Nexus.
 */
public enum TaskStatus {
    // TODO deve ser o status quando criar uma task nova
    TO_DO {
        @Override
        public String toString() { return "A Fazer"; }
    }, 
    IN_PROGRESS {
        @Override
        public String toString() { return "Em Progresso"; }
    }, 
    BLOCKED {
        @Override
        public String toString() { return "Bloqueada"; }
    }, 
    DONE {
        @Override
        public String toString() { return "Concluída"; }
    }
}
