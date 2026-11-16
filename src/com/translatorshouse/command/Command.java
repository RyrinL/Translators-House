package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;

/** COMMAND. Every parsed input becomes one of these; the game loop just calls execute(). */
public interface Command {
    void execute(GameWorld world);
    String describe();
}
