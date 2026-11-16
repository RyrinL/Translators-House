package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;

public class UndoCommand implements Command {

    public void execute(GameWorld world) {
        if (world.undo()) {
            world.getConsole().println("You step back a moment in time.");
            LookCommand.render(world);
        } else {
            world.getConsole().println("There's nothing to undo.");
        }
    }

    public String describe() { return "undo"; }
}
