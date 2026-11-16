package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.persistence.SaveFile;

import java.io.IOException;

public class LoadCommand implements Command {
    private final String path;

    public LoadCommand(String path) { this.path = path; }

    public void execute(GameWorld world) {
        if (!SaveFile.exists(path)) {
            world.getConsole().println("No save file at " + path + ".");
            return;
        }
        try {
            world.loadGame(path);
            world.getConsole().println("Loaded from " + path + ".");
            LookCommand.render(world);
        } catch (IOException e) {
            world.getConsole().println("Could not load: " + e.getMessage());
        }
    }

    public String describe() { return "load " + path; }
}
