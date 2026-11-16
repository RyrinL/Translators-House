package com.translatorshouse.item;

import com.translatorshouse.text.Messages;

/** Creates fresh items/containers by id. Items are identified by id, so snapshots store
 *  only ids and rebuild objects here on restore. */
public final class ItemFactory {
    private ItemFactory() {}

    public static Item create(String id) {
        return switch (id) {
            case "journal"      -> new SimpleItem(id, "journal", Messages.JOURNAL_DESC, true);
            case "portrait"     -> new SimpleItem(id, "portrait", Messages.PORTRAIT_DESC, false);
            case "matchbook"    -> new SimpleItem(id, "matchbook", Messages.MATCHBOOK_DESC, true);
            case "dishtowel"    -> new SimpleItem(id, "dishtowel", Messages.DISHTOWEL_DESC, true);
            case "teapot"       -> new SimpleItem(id, "teapot", Messages.TEAPOT_DESC, false);
            case "frozen-key"   -> new SimpleItem(id, "frozen-key", Messages.FROZEN_KEY_DESC, true);
            case "brass-key"    -> new SimpleItem(id, "brass-key", Messages.BRASS_KEY_DESC, true);
            case "diary-1"   -> new SimpleItem(id, "diary-1", Messages.DICT_ID_EN_DESC, true);
            case "diary-2"   -> new SimpleItem(id, "diary-2", Messages.DICT_EN_ZH_DESC, true);
            case "diary-3"   -> new SimpleItem(id, "diary-3", Messages.DICT_ZH_ID_DESC, true);
            case "small-mirror" -> new SimpleItem(id, "small-mirror", Messages.SMALL_MIRROR_DESC, true);
            case "riddle-scroll"-> new SimpleItem(id, "riddle-scroll", Messages.RIDDLE_DESC, true);
            case "reflection-left"   -> new SimpleItem(id, "reflection-left", Messages.REFLECTION_DESC, true);
            case "reflection-center" -> new SimpleItem(id, "reflection-center", Messages.REFLECTION_DESC, true);
            case "reflection-right"  -> new SimpleItem(id, "reflection-right", Messages.REFLECTION_DESC, true);
            case "moonstone"    -> new SimpleItem(id, "moonstone", Messages.MOONSTONE_DESC, true);
            case "fountain"     -> new SimpleItem(id, "fountain", Messages.FOUNTAIN_DESC, false);
            case "gate"         -> new SimpleItem(id, "gate", Messages.GATE_DESC, false);
            // containers
            case "freezer"        -> new Container(id, "freezer", Messages.FREEZER_DESC, false, false);
            case "library-drawer" -> new Container(id, "library-drawer", Messages.DRAWER_DESC, false, true);
            case "library-shelf"  -> new Container(id, "library-shelf", Messages.SHELF_DESC, true, false);
            default -> new SimpleItem(id, id, "An indistinct thing.", false);
        };
    }
}
