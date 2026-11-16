package com.translatorshouse;

import com.translatorshouse.core.GameFacade;

public class Main {
    public static void main(String[] args) {
        GameFacade game = new GameFacade();
        game.start();
        while (!game.isOver()) {
            game.runOneTurn();
        }
        game.end();
    }
}
