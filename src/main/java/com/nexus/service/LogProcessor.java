package com.nexus.service;

import com.nexus.model.*;
import com.nexus.exception.NexusValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LogProcessor {

    public void processLog(String fileName, Workspace workspace, List<User> users) {
        try {
            // Busca o arquivo dentro da pasta de recursos do projeto (target/classes)
            var resource = getClass().getClassLoader().getResourceAsStream(fileName);
            
            if (resource == null) {
                throw new IOException("Arquivo não encontrado no classpath: " + fileName);
            }

            try (java.util.Scanner s = new java.util.Scanner(resource).useDelimiter("\\A")) {
                String content = s.hasNext() ? s.next() : "";
                List<String> lines = List.of(content.split("\\R"));
                
                for (String line : lines) {
                    if (line.isBlank() || line.startsWith("#")) continue;

                    String[] p = line.split(";");
                    String action = p[0];

                    try {
                        switch (action) {
                            case "CREATE_USER" -> {
                                users.add(new User(p[1], p[2]));
                                System.out.println("[LOG] Usuário criado: " + p[1]);
                            }
                            case "CREATE_TASK" -> {
                                Task t = new Task(p[1], LocalDate.parse(p[2]));
                                workspace.addTask(t);
                                System.out.println("[LOG] Tarefa criada: " + p[1]);
                            }
                            case "CREATE_PROJECT" -> {
                                Project pj = new Project(p[1], p[2]);
                                System.out.println("[LOG] Projeto criado: " + p[1]);
                            }
                            case "ASSIGN_USER" -> {
                                List<Task> tasksList = workspace.getTasks(); // ta errado
                                Task t = taskList.stream().filter(m -> m.id.equals(int(p[1]))).findFirst();
                                if (t.equals(null)){throw new NexusValidationException("Nao existe task com esse id");}
                                User u = users.stream().filter(m -> m.username.equals(username)).findFirst();
                                if (u.equals(null)) {throw new NexusValidationException("Nao existe user com esse username")}
                                t.owner = u;
                                System.out.println("[LOG] A tarefa com id" + p[1] + "fora designada
                                ao usuario com nome" + [2]);
                            }
                            case "CHANGE_STATUS" -> {
                                List<Task> tasksList = workspace.getTasks(); // ta errado
                                Task t = taskList.stream().filter(m -> m.id.equals(int(p[1]))).findFirst();
                                if (t.equals(null)){throw new NexusValidationException("Nao existe task com esse id");}
                                switch(p[2]) {
                                    case "IN_PROGRESS" -> {t.markAsInProgress();}
                                    case "DONE" -> {t.markAsDone()}
                                    case "BLOCKED" -> {t.markAsBlocked(1)}
                                }
                            }
                            case "REPORT_STATUS" -> {
                                // printa os relatorios analiticos streams no console
                                // coletar usuarios com mais de 10 tarefas em todo....
                            }
                            default -> System.err.println("[WARN] Ação desconhecida: " + action);
                        }
                    } catch (NexusValidationException e) {
                        System.err.println("[ERRO DE REGRAS] Falha no comando '" + line + "': " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[ERRO FATAL] " + e.getMessage());
        }
    }
}