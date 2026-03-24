package com.nexus.service;

import com.nexus.model.*;
import com.nexus.exception.NexusValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                                if (p[1].isBlank() || p[1] == null || p[2].isBlank() || p[2] == null){
                                    throw new IllegalArgumentException("Parametros incorretos.");
                                }
                                users.add(new User(p[1], p[2]));
                                System.out.println("[LOG] Usuário criado: " + p[1]);
                                break;
                            }
                            case "CREATE_TASK" -> {
                                if (p[1].isBlank() || p[1] == null || p[2].isBlank() || p[2] == null
                                || p[3].isBlank() || p[3] == null || p[4].isBlank() || p[4] == null){
                                    throw new IllegalArgumentException("Parametros incorretos.");
                                }
                                Project pj = workspace.stream().filter(m -> m.consultProjectName().equals
                                ((String)p[4])).findFirst();
                                if (pj == null) {
                                    throw new NexusValidationException("Nao existe project com esse nome");
                                }
                                Task t = new Task(p[1], LocalDate.parse(p[2], p[3], pj));
                                workspace.addTask(t);
                                System.out.println("[LOG] Tarefa criada: " + p[1]);
                                break;
                            }
                            case "CREATE_PROJECT" -> {
                                if (p[1].isBlank() || p[1] == null || p[2].isBlank() || p[2] == null){
                                    throw new IllegalArgumentException("Parametros incorretos.");
                                }
                                Project name = workspace.stream().filter(m -> m.consultProjectName().equals(
                                (String)p[1])).findFirst();
                                if (name != null) {
                                    throw new NexusValidationException("Ja existe um projeto com esse nome");
                                } else [
                                    Project pj = new Project(p[1], Integer.parseInt(p[2]));
                                ]
                                System.out.println("[LOG] Projeto criado: " + p[1]);
                                break;
                            }
                            case "ASSIGN_USER" -> {
                                if (p[1].isBlank() || p[1] == null || p[2].isBlank() || p[2] == null){
                                    throw new IllegalArgumentException("Parametros incorretos.");
                                }
                                List<Task> tasksList = workspace.getTasks();
                                Optional<Task> _t = tasksList.stream().filter(m -> m.getId() == Integer.parseInt(p[1])).findFirst();
                                User u;
                                Task t;
                                if (!_t.isPresent()){throw new NexusValidationException("Nao existe task com esse id");}
                                else {
                                    t = _t.get();
                                }
                                Optional<User> _u = users.stream().filter(m -> m.consultUsername().equals(p[2])).findFirst();
                                if (!_u.isPresent()) {throw new NexusValidationException("Nao existe user com esse username");}
                                else{u = _u.get(); }
                                t.setOwner(u);
                                System.out.println("[LOG] A tarefa com id " + p[1] + " fora designada ao usuário com nome " + p[2]);
                                break;
                            }
                            case "CHANGE_STATUS" -> {
                                if (p[1].isBlank() || p[1] == null || p[2].isBlank() || p[2] == null){
                                    throw new IllegalArgumentException("Parametros incorretos.");
                                }
                                Task t;
                                List<Task> tasksList = workspace.getTasks(); // ta errado
                                Optional<Task> _t = tasksList.stream().filter(m -> m.getId() == Integer.parseInt(p[1])).findFirst();
                                if (!_t.isPresent()){throw new NexusValidationException("Nao existe task com esse id");}
                                else {t = _t.get();}
                                switch(p[2]) {
                                    case "IN_PROGRESS" -> {
                                        t.markAsInProgress();
                                        break;    
                                    }
                                    case "DONE" -> {
                                        t.markAsDone();
                                        break;    
                                    }
                                    case "BLOCKED" -> {
                                        t.markAsBlocked(true);
                                        break;   
                                    }
                                }
                                break;
                            }
                            case "REPORT_STATUS" -> {
                                // printa os relatorios analiticos streams no console
                                // coletar usuarios com mais de 10 tarefas em todo....
                                workspace.report_Status();
                                break;
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