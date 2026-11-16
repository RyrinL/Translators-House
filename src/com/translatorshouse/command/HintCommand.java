package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.text.Messages;

/** Reads the player's progress (world flags) and suggests the next step.
 *  Keeps a first-timer unstuck without needing an external walkthrough. */
public class HintCommand implements Command {

    public void execute(GameWorld world) {
        String hint;
        if (!world.getBooleanFlag("journal_read")) {
            hint = Messages.HINT_JOURNAL;
        } else if (!world.getBooleanFlag("mirror_revealed")) {
            hint = Messages.HINT_PORTRAIT;
        } else if (!world.getBooleanFlag("brass_key_obtained")) {
            hint = Messages.HINT_KEY;
        } else if (!world.getBooleanFlag("moonstone_obtained")) {
            if (!world.getBooleanFlag("library_solved")) {
                hint = Messages.HINT_LIBRARY;
            } else if (!world.getPlayer().getInventory().contains("riddle-scroll")) {
                hint = Messages.HINT_SCROLL;
            } else {
                hint = Messages.HINT_MIRROR;
            }
        } else if (!world.getBooleanFlag("gate_open")) {
            hint = Messages.HINT_GATE;
        } else {
            hint = Messages.HINT_DONE;
        }
        world.getConsole().println(hint);
    }

    public String describe() { return "hint"; }
}
