package com.nexus.model;
import java.util.List

public class User {
    private final String username;
    private final String email;

    public User(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username não pode ser vazio.");
        }
        if (!email.matches("*" + "@" + "*" + ".com")) {
            throw new IllegalArgumentException("O email nao segue a formatacao desejada.");        
        }
        this.username = username;
        this.email = email;
    }

    public String consultEmail() {
        return email;
    }

    public String consultUsername() {
        return username;
    }

    public long calculateWorkload(Workspace workS) {
        t = workS.getTasks();
        run = true;
        i = 0, len = 0;
        Task cur = t[i];
        while (run) {
            if (cur.owner.consultUsername(username).equals(this.consultUsername(username)) && cur.status == TaskStatus.IN_PROGRESS) {
                len++;
            }
                try {
                    i++
                    cur = t[i];
                }
                catch(exception e) {
                }
        }
        return len; 
    }

