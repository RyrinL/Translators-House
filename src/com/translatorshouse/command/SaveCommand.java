package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;

import java.io.IOException;

public class SaveCommand implements Command {
    private final String path;

    public SaveCommand(String path) { this.path = path; }

    public void execute(GameWorld world) {
        try {
            world.saveGame(path);
            world.getConsole().println("Saved to " + path + ".");
        } catch (IOException e) {
            world.getConsole().println("Could not save: " + e.getMessage());
        }
    }

    public String describe() { return "save " + path; }
}
