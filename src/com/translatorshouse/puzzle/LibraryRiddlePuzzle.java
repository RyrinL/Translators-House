package com.translatorshouse.puzzle;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Container;
import com.translatorshouse.item.ItemFactory;
import com.translatorshouse.text.Messages;

public class LibraryRiddlePuzzle extends AbstractPuzzle {

    // diaries read in volume order: diary-1, diary-2, diary-3
    private static final String CORRECT = "diary-1,diary-2,diary-3";

    protected String getId() { return "library-riddle"; }

    protected boolean validateInput(String input) {
        return input != null && !input.isBlank();
    }

    protected boolean checkSolution(String input, GameWorld world) {
        return !world.getBooleanFlag("library_solved") && input.endsWith(CORRECT);
    }

    protected void applyEffects(GameWorld world) {
        Container drawer = world.container("library-drawer");
        drawer.unlock();
        drawer.open();
        drawer.add(ItemFactory.create("riddle-scroll"));
        world.setFlag("library_solved", true);
    }

    protected String rejectionMessage()  { return Messages.PUZZLE_LIBRARY_WRONG; }
    protected String wrongAnswerMessage() { return Messages.PUZZLE_LIBRARY_WRONG; }
    protected String successMessage()     { return Messages.PUZZLE_LIBRARY_SOLVED; }
}
