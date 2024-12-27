package com.android.systemui.qs.external;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class TileRequestDialogEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ TileRequestDialogEvent[] $VALUES;
    public static final TileRequestDialogEvent TILE_REQUEST_DIALOG_DISMISSED;
    public static final TileRequestDialogEvent TILE_REQUEST_DIALOG_SHOWN;
    public static final TileRequestDialogEvent TILE_REQUEST_DIALOG_TILE_ADDED;
    public static final TileRequestDialogEvent TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED;
    public static final TileRequestDialogEvent TILE_REQUEST_DIALOG_TILE_NOT_ADDED;
    private final int _id;

    static {
        TileRequestDialogEvent tileRequestDialogEvent = new TileRequestDialogEvent("TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED", 0, 917);
        TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED = tileRequestDialogEvent;
        TileRequestDialogEvent tileRequestDialogEvent2 = new TileRequestDialogEvent("TILE_REQUEST_DIALOG_SHOWN", 1, 918);
        TILE_REQUEST_DIALOG_SHOWN = tileRequestDialogEvent2;
        TileRequestDialogEvent tileRequestDialogEvent3 = new TileRequestDialogEvent("TILE_REQUEST_DIALOG_DISMISSED", 2, 919);
        TILE_REQUEST_DIALOG_DISMISSED = tileRequestDialogEvent3;
        TileRequestDialogEvent tileRequestDialogEvent4 = new TileRequestDialogEvent("TILE_REQUEST_DIALOG_TILE_ADDED", 3, 920);
        TILE_REQUEST_DIALOG_TILE_ADDED = tileRequestDialogEvent4;
        TileRequestDialogEvent tileRequestDialogEvent5 = new TileRequestDialogEvent("TILE_REQUEST_DIALOG_TILE_NOT_ADDED", 4, 921);
        TILE_REQUEST_DIALOG_TILE_NOT_ADDED = tileRequestDialogEvent5;
        TileRequestDialogEvent[] tileRequestDialogEventArr = {tileRequestDialogEvent, tileRequestDialogEvent2, tileRequestDialogEvent3, tileRequestDialogEvent4, tileRequestDialogEvent5};
        $VALUES = tileRequestDialogEventArr;
        EnumEntriesKt.enumEntries(tileRequestDialogEventArr);
    }

    private TileRequestDialogEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static TileRequestDialogEvent valueOf(String str) {
        return (TileRequestDialogEvent) Enum.valueOf(TileRequestDialogEvent.class, str);
    }

    public static TileRequestDialogEvent[] values() {
        return (TileRequestDialogEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}
