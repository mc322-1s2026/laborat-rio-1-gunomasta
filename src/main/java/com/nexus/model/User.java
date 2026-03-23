package com.nexus.model;
import com.nexus.model.User;
import com.nexus.model.Task;
import java.util.List;
import com.nexus.service.Workspace;

public class User {
    private final String username;
    private final String email;

    public User(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username não pode ser vazio.");
        }
        if (!email.matches(".+@.+.com")) {
            throw new IllegalArgumentException("O email não segue a formatação desejada.");        
        }
        this.username = username;
        this.email = email.toLowerCase();
    }

    public String consultEmail() {
        return email;
    }

    public String consultUsername() {
        return username;
    }

    public int calculateWorkload(Workspace w) {
        List<Task> tasks = w.getTasks();
        int res = tasks.stream().filter(m -> m.getStatus().equals(TaskStatus.IN_PROGRESS) && m.getOwner().equals(this)).count();
        return res;
    }
}

