package com.translatorshouse.event;

public class PuzzleSolvedEvent implements WorldEvent {
    private final String puzzleId;
    public PuzzleSolvedEvent(String puzzleId) { this.puzzleId = puzzleId; }
    public String getPuzzleId() { return puzzleId; }
    public String name() { return "PuzzleSolved"; }
}
