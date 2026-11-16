package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;

public class QuitCommand implements Command {
    public void execute(GameWorld world) { world.setOver(true); }
    public String describe() { return "quit"; }
}
