package org.tensorflow.demo;

public class Account {
    private int id,level;
    private String username;

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public String getUsername() {
        return username;
    }
    public Account(int id, String username, int level) {
        this.id = id;
        this.username = username;
        this.level = level;
    }
}
