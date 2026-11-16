package com.translatorshouse.core;

import com.translatorshouse.command.Command;
import com.translatorshouse.command.CommandParser;
import com.translatorshouse.command.LookCommand;
import com.translatorshouse.text.Messages;
import com.translatorshouse.util.ConsoleIO;

/** FACADE. Hides the world, parser, event bus and console behind a tiny interface for Main. */
public class GameFacade {
    private final GameWorld world;
    private final CommandParser parser;
    private final ConsoleIO console;

    public GameFacade() {
        this.world = GameWorld.getInstance();
        this.parser = new CommandParser();
        this.console = new ConsoleIO();
        this.world.setConsole(console);
    }

    public void start() {
        world.initialize();
        console.println(Messages.GAME_START);
        LookCommand.render(world);
    }

    public void runOneTurn() {
        console.blank();
        console.print("> ");
        String input = console.readLine();
        world.snapshotForUndo();          // MEMENTO: snapshot before every command (enables undo)
        Command cmd = parser.parse(input); // COMMAND: input -> object
        cmd.execute(world);
    }

    public boolean isOver() { return world.isOver(); }

    public void end() { console.println(Messages.GAME_THANKS); }
}
