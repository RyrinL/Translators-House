package com.translatorshouse.puzzle;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Container;
import com.translatorshouse.item.ItemFactory;
import com.translatorshouse.text.Messages;

public class FreezerPuzzle extends AbstractPuzzle {

    protected String getId() { return "freezer"; }

    protected boolean validateInput(String input) {
        String s = input.toLowerCase();
        return s.contains("frozen-key") && s.contains("fireplace");
    }

    protected boolean checkSolution(String input, GameWorld world) {
        return world.getBooleanFlag("fireplace_lit");
    }

    protected void applyEffects(GameWorld world) {
        world.getPlayer().getInventory().remove("frozen-key");
        world.getPlayer().getInventory().add(ItemFactory.create("brass-key"));
        world.setFlag("freezer_melted", true);
    }

    protected String rejectionMessage()  { return Messages.PUZZLE_FREEZER_INVALID; }
    protected String wrongAnswerMessage() { return Messages.PUZZLE_FREEZER_WRONG; }
    protected String successMessage()     { return Messages.PUZZLE_FREEZER_SOLVED; }
}
