package com.translatorshouse.puzzle;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.event.PuzzleSolvedEvent;

//TEMPLATE METHOD. attempt() fixes the skeleton; subclasses fill in the steps.
public abstract class AbstractPuzzle {

    public final PuzzleResult attempt(String input, GameWorld world) {
        if (!validateInput(input)) {
            return PuzzleResult.invalid(rejectionMessage());
        }
        if (!checkSolution(input, world)) {
            return PuzzleResult.wrong(wrongAnswerMessage());
        }
        applyEffects(world);
        world.getEventBus().publish(new PuzzleSolvedEvent(getId()));
        return PuzzleResult.solved(successMessage());
    }

    protected abstract String getId();
    protected abstract boolean validateInput(String input);
    protected abstract boolean checkSolution(String input, GameWorld world);
    protected abstract void applyEffects(GameWorld world);
    protected abstract String rejectionMessage();
    protected abstract String wrongAnswerMessage();
    protected abstract String successMessage();
}
