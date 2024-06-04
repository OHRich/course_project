package org.example.tanchiki.models;

import org.example.tanchiki.Game;
import org.example.tanchiki.services.ServerTask;

import java.util.Objects;

public class Player {
    private final String username;

    private final String password;

    private int lastLevel;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.lastLevel = 1;
    }

    public Player(String username, String password, int lastLevel) {
        this.username = username;
        this.password = password;
        this.lastLevel = lastLevel;
    }

    public void levelUp() {
        if (lastLevel < Game.getLevels().getLast().num()) {
            ServerTask.levelUp("levelup", username, password, ++lastLevel);
        }
    }

    public String getUsername() {
        return username;
    }

    public int getLastStage() {
        return lastLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return username.equals(player.username) && password.equals(player.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
