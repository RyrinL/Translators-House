package com.translatorshouse.command;

import java.util.Arrays;

/** Turns a raw input line into a Command object. Deliberately small — a tight vocabulary. */
public class CommandParser {

    public Command parse(String input) {
        if (input == null || input.isBlank()) return new UnknownCommand();
        String[] tokens = input.trim().toLowerCase().split("\\s+");
        String verb = tokens[0];
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (verb) {
            case "look", "l" -> new LookCommand();
            case "go", "move" -> new MoveCommand(args.length > 0 ? args[0] : "");
            case "n", "north" -> new MoveCommand("north");
            case "s", "south" -> new MoveCommand("south");
            case "e", "east"  -> new MoveCommand("east");
            case "w", "west"  -> new MoveCommand("west");
            case "mirror"     -> new MoveCommand("mirror");
            case "out", "back" -> new MoveCommand("out");
            case "take", "get", "pick" -> new TakeCommand(joinArgs(args));
            case "examine", "x", "inspect" -> new ExamineCommand(joinArgs(args));
            case "use" -> UseCommand.fromTokens(args);
            case "read" -> new ReadCommand(joinArgs(args));
            case "inventory", "i", "inv" -> new InventoryCommand();
            case "hint" -> new HintCommand();
            case "save" -> new SaveCommand(args.length > 0 ? args[0] : "save.dat");
            case "load" -> new LoadCommand(args.length > 0 ? args[0] : "save.dat");
            case "undo" -> new UndoCommand();
            case "quit", "exit" -> new QuitCommand();
            case "help", "?" -> new HelpCommand();
            default -> new UnknownCommand();
        };
    }

    private String joinArgs(String[] args) {
        return String.join(" ", args);
    }
}
