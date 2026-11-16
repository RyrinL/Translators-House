package com.translatorshouse.puzzle;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.ItemFactory;
import com.translatorshouse.text.Messages;

public class MirrorRoomPuzzle extends AbstractPuzzle {

    protected String getId() { return "mirror-room"; }

    protected boolean validateInput(String input) {
        return input != null && input.startsWith("reflection-");
    }

    // The truthful mirror is named only in the library riddle scroll, so the
    // library is required: the position cannot be guessed from the room alone.
    public static final String TRUE_REFLECTION = "reflection-left";

    protected boolean checkSolution(String input, GameWorld world) {
        return input.equals(TRUE_REFLECTION);
    }

    protected void applyEffects(GameWorld world) {
        // the true reflection becomes a moonstone
        world.floor("mirrorroom").removeItem("reflection-left");
        world.floor("mirrorroom").removeItem("reflection-center");
        world.floor("mirrorroom").removeItem("reflection-right");
        world.getPlayer().getInventory().add(ItemFactory.create("moonstone"));
        world.setFlag("moonstone_obtained", true);
    }

    protected String rejectionMessage()  { return Messages.PUZZLE_MIRROR_WRONG; }
    protected String wrongAnswerMessage() { return Messages.PUZZLE_MIRROR_WRONG; }
    protected String successMessage()     { return Messages.MIRROR_RIGHT; }
}
