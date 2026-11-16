package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.text.Messages;

public class UnknownCommand implements Command {
    public void execute(GameWorld world) { world.getConsole().println(Messages.UNKNOWN); }
    public String describe() { return "unknown"; }
}
