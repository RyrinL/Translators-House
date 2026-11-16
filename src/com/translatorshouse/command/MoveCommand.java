package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.event.GameEndEvent;
import com.translatorshouse.room.Room;
import com.translatorshouse.text.Messages;

public class MoveCommand implements Command {
    private final String direction;

    public MoveCommand(String direction) { this.direction = direction == null ? "" : direction; }

    public void execute(GameWorld world) {
        if (direction.isBlank()) {
            world.getConsole().println(Messages.CANT_GO);
            return;
        }
        Room room = world.getCurrentRoom();
        if (!room.canMoveTo(direction, world)) {
            // a locked room prints its own reason and still reports a destination;
            // only print the generic message for a direction that isn't an exit at all.
            if (room.destinationFor(direction, world).isBlank()) {
                world.getConsole().println(Messages.CANT_GO);
            }
            return;
        }
        String dest = room.destinationFor(direction, world);
        if (dest.equals("END")) {
            world.getEventBus().publish(new GameEndEvent());
            return;
        }
        world.moveTo(dest);
        LookCommand.render(world);
    }

    public String describe() { return "go " + direction; }
}
