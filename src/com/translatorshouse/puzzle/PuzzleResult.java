package com.translatorshouse.puzzle;

/** Result of a puzzle attempt. */
public final class PuzzleResult {
    public enum Status { INVALID, WRONG, SOLVED }

    private final Status status;
    private final String message;

    private PuzzleResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static PuzzleResult invalid(String m) { return new PuzzleResult(Status.INVALID, m); }
    public static PuzzleResult wrong(String m)   { return new PuzzleResult(Status.WRONG, m); }
    public static PuzzleResult solved(String m)  { return new PuzzleResult(Status.SOLVED, m); }

    public Status status() { return status; }
    public String message() { return message; }
    public boolean isSolved() { return status == Status.SOLVED; }
    public boolean isWrong()  { return status == Status.WRONG; }
}
