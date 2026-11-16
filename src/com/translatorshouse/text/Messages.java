package com.translatorshouse.text;

/** All player-facing English text lives here as constants. No string literals scattered in logic. */
public final class Messages {
    private Messages() {}

    public static final String GAME_START = """
            THE TRANSLATOR'S HOUSE
            ----------------------
            You wake on a made bed in a room you do not remember entering.
            The air is still. Somewhere, faintly, the house is waiting.

            New here? Type 'help' for commands, or 'hint' if you get stuck.
            A good first move is to look around and read what's on the nightstand.
            """;

    public static final String GAME_THANKS = "\nThank you for visiting the translator's house.";
    public static final String HELP = """
            Commands:
              look (l)              - describe the room
              go <dir> / n,s,e,w    - move (also 'go mirror' once revealed)
              examine <thing> (x)   - look closely at something
              take <item>           - pick something up
              use <a> on <b>        - use an item on something
              read <item>           - read a book, journal, or scroll
              inventory (i)         - list what you carry (walks the inventory)
              save / load           - save or restore to a file
              undo                  - step back one move
              hint                  - suggest your next step
              help (?)  /  quit
            """;

    // generic
    public static final String CANT_FIND_ITEM = "You don't see that here.";
    public static final String CANT_TAKE_THAT = "You can't take that.";
    public static final String TAKEN = "Taken: ";
    public static final String NOTHING_HAPPENS = "Nothing happens.";
    public static final String UNKNOWN = "You're not sure how to do that. (try 'help')";
    public static final String CANT_GO = "You can't go that way.";
    public static final String NOT_CARRYING = "You aren't carrying that.";
    public static final String INVENTORY_EMPTY = "Your hands are empty.";
    public static final String INVENTORY_HEADER = "You are carrying:";

    // bedroom
    public static final String ROOM_BEDROOM_DESCRIPTION = """
            A small bedroom. The bed is neatly made and was never slept in.
            A nightstand holds a worn leather journal. The window is painted shut.
            A door leads north, though it won't budge yet.""";
    public static final String BEDROOM_DOOR_LOCKED = "The north door is stuck fast. Perhaps the journal knows something.";
    public static final String BEDROOM_DOOR_OPENS = "Having read the journal, you heard a 'click' sound from the door.";

    // hallway
    public static final String ROOM_HALLWAY_DESCRIPTION = """
            A long, narrow hallway with four doors. At the end hangs a portrait of a
            woman holding a teacup. Doors lead south, east, and west; the north door
            opens onto the garden.""";
    public static final String PORTRAIT_FIRST = "She is holding a teacup, mid-sip, her eyes turned slightly away.";
    public static final String PORTRAIT_SECOND = "Looking again, you notice the wall behind the portrait is hollow. A hidden door swings open - a Mirror Room.";
    public static final String PORTRAIT_AFTER = "The painted woman watches you go.";

    // kitchen
    public static final String ROOM_KITCHEN_DESCRIPTION = """
            A warm kitchen that still smells faintly of bread. A teapot sits on the
            stove and a dark fireplace waits in the wall. A chest freezer hums in the
            corner; through its frost you can see a key sealed in a block of ice.""";
    public static final String FIREPLACE_LIT = "The matchbook flares; the fireplace catches and warms the room.";
    public static final String FIREPLACE_ALREADY = "The fireplace is already burning.";
    public static final String NO_MATCH = "You have nothing to light it with.";

    // library
    public static final String ROOM_LIBRARY_DESCRIPTION = """
            A wall of books - countless titles in a dozen languages.
            Three slim volumes sit slightly proud of the shelf; they look like diaries.
            A writing desk holds a single locked drawer.""";
    public static final String DRAWER_LOCKED = "The drawer is locked. Something must trip it open.";
    public static final String DICT_READ = "You read from the diary, tracing the handwriting in the margins.";
    public static final String RIDDLE_APPEARS = "A soft 'click' - the desk drawer springs open. A riddle scroll lies inside.";

    // mirror room
    public static final String ROOM_MIRROR_DESCRIPTION = """
            Three full-length mirrors face you, on the left, center, and right walls.
            Each shows a slightly different you, and they look maddeningly alike.
            Only one reflection is true. Choosing wrong feels unwise.
            (You can 'take reflection-left', '...-center', or '...-right'.)""";
    public static final String MIRROR_WRONG = "The glass ripples wrongly. You blink - and you are back on the made bed, as if from a dream.";
    public static final String MIRROR_RIGHT = "Your true reflection lifts free of the glass and cools into a smooth moonstone in your palm.";

    // garden
    public static final String ROOM_GARDEN_DESCRIPTION = """
            The garden behind the house, silver under the moon. A fountain murmurs at its
            center. At the back wall stands a stone gate.""";
    public static final String FOUNTAIN = "Carved around the fountain's rim: 'For every word I could not carry across, I left a stone.'";
    public static final String GATE_LOCKED = "The gate needs both the brass key and the moonstone to open.";
    public static final String GATE_OPENS = "Brass key and moonstone together - the gate swings wide onto the road beyond.";

    // puzzle: freezer
    public static final String PUZZLE_FREEZER_INVALID = "Use what on what? (try: use frozen-key on fireplace)";
    public static final String PUZZLE_FREEZER_WRONG = "The ice won't melt while the fireplace is cold.";
    public static final String PUZZLE_FREEZER_SOLVED = "You hold the frozen key to the fire. The ice sloughs away, leaving a brass key.";

    // puzzle: library
    public static final String PUZZLE_LIBRARY_WRONG = "You read them, but in no order that means anything.";
    public static final String PUZZLE_LIBRARY_SOLVED = "A soft 'click' - the desk drawer springs open. A riddle scroll lies inside.";

    // puzzle: mirror
    public static final String PUZZLE_MIRROR_WRONG = "That reflection is a lie.";
    public static final String PUZZLE_MIRROR_SOLVED = "True.";

    // hints (context-aware, chosen by HintCommand based on progress)
    public static final String HINT_JOURNAL = "You're in the bedroom. Try 'read journal' on the nightstand. It may loosen the north door.";
    public static final String HINT_PORTRAIT = "Explore the hallway. The portrait at the end rewards a closer look. Try 'examine portrait' more than once.";
    public static final String HINT_KEY = "The kitchen (east of the hallway) has a matchbook and a frozen key. Light the fireplace, then melt the ice: 'take matchbook', 'use matchbook on fireplace', 'take frozen-key', 'use frozen-key on fireplace', then 'take brass-key'.";
    public static final String HINT_LIBRARY = "Visit the library (west of the hallway). Read the three diaries in order: 'read diary-1', 'read diary-2', 'read diary-3' to open the desk drawer and take the riddle-scroll.";
    public static final String HINT_SCROLL = "The drawer is open. 'take riddle-scroll' and 'read riddle-scroll' - it names which mirror is true.";
    public static final String HINT_MIRROR = "Go to the Mirror Room (revealed off the hallway). The riddle scroll said which mirror is truthful. 'take' that one. Guessing wrong sends you back.";
    public static final String HINT_GATE = "Head to the garden (north of the hallway) and 'use brass-key on gate'. You need both the brass key and the moonstone.";
    public static final String HINT_DONE = "The gate is open. Walk out: 'go gate'.";

    // events / journal
    public static final String JOURNAL_INTRO = "The journal is hers. The first page reads: 'If you are reading this, the house has chosen to let you in.'";
    public static final String JOURNAL_ENTRY_PREFIX = "  [journal] ";
    public static final String KEY_OBTAINED = "A brass key - it feels important.";

    // ending
    public static final String ENDING = """

            You step through the gate. The house settles behind you, content.
            Somewhere a teacup is set down. THE END.
            """;

    // descriptions for items
    public static final String JOURNAL_DESC = "A leather journal, soft with handling.";
    public static final String PORTRAIT_DESC = "A portrait of the translator, holding a teacup.";
    public static final String MATCHBOOK_DESC = "A half-used matchbook.";
    public static final String DISHTOWEL_DESC = "A faded dishtowel. It does nothing in particular.";
    public static final String FROZEN_KEY_DESC = "A key sealed inside a block of ice.";
    public static final String BRASS_KEY_DESC = "A small brass key, still cold.";
    public static final String DICT_ID_EN_DESC = "A worn diary - Vol. 1. Her handwriting fills the margins.";
    public static final String DICT_EN_ZH_DESC = "A worn diary - Vol. 2. More notes, more crossings-out.";
    public static final String DICT_ZH_ID_DESC = "A worn diary - Vol. 3. The last entries are barely legible.";
    public static final String SMALL_MIRROR_DESC = "A small hand mirror.";
    public static final String RIDDLE_DESC = "The scroll reads: 'Three mirrors, three faces, one truth. The truthful glass hangs on the LEFT wall, where the morning light never flattered her.'";
    public static final String REFLECTION_DESC = "A reflection in a tall mirror.";
    public static final String MOONSTONE_DESC = "A smooth, cool moonstone.";
    public static final String FREEZER_DESC = "A chest freezer, frost on the lid.";
    public static final String DRAWER_DESC = "A writing-desk drawer.";
    public static final String SHELF_DESC = "Countless books in a dozen languages. Three slim diaries sit slightly proud of the rest.";
    public static final String FOUNTAIN_DESC = "A moonlit fountain.";
    public static final String GATE_DESC = "A stone garden gate.";
    public static final String TEAPOT_DESC = "A teapot, long cold.";
}
