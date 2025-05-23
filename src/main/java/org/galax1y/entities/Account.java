package org.galax1y.entities;

import java.util.Objects;
import java.util.UUID;

public class Account {
    private final UUID id;

    private String name;
    private String email;
    private String password;

    public Account(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static Account create(String name, String email, String password) {
        return new Account(name, email, password);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordsMatch(String password) {
        return Objects.equals(password, this.getPassword());
    }
}
