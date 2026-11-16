package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.text.Messages;

public class HelpCommand implements Command {
    public void execute(GameWorld world) { world.getConsole().println(Messages.HELP); }
    public String describe() { return "help"; }
}
