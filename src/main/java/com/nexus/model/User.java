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

    public long calculateWorkload(Workspace workS) {
        List<Task> t = workS.getTasks();
        boolean run = true;
        int i = 0, len = 0;
        Task cur = t.get(i);
        while (run) {
            if (cur.getOwner().consultUsername().equals(this.consultUsername()) && cur.getStatus() == TaskStatus.IN_PROGRESS) {
                len++;
            }
                try {
                    i++;
                    cur = t.get(i);
                }
                catch(Exception e) {
                }
        }
        return len; 
    }
}

