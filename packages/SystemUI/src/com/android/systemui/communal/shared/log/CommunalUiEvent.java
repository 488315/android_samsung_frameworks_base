package com.android.systemui.communal.shared.log;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class CommunalUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ CommunalUiEvent[] $VALUES;
    public static final CommunalUiEvent COMMUNAL_HUB_EDIT_MODE_GONE;
    public static final CommunalUiEvent COMMUNAL_HUB_EDIT_MODE_SHOWN;
    public static final CommunalUiEvent COMMUNAL_HUB_GONE;
    public static final CommunalUiEvent COMMUNAL_HUB_REORDER_WIDGET_CANCEL;
    public static final CommunalUiEvent COMMUNAL_HUB_REORDER_WIDGET_FINISH;
    public static final CommunalUiEvent COMMUNAL_HUB_REORDER_WIDGET_START;
    public static final CommunalUiEvent COMMUNAL_HUB_SHOWN;
    public static final CommunalUiEvent COMMUNAL_HUB_SWIPE_TO_ENTER_CANCEL;
    public static final CommunalUiEvent COMMUNAL_HUB_SWIPE_TO_ENTER_FINISH;
    public static final CommunalUiEvent COMMUNAL_HUB_SWIPE_TO_ENTER_START;
    public static final CommunalUiEvent COMMUNAL_HUB_SWIPE_TO_EXIT_CANCEL;
    public static final CommunalUiEvent COMMUNAL_HUB_SWIPE_TO_EXIT_FINISH;
    public static final CommunalUiEvent COMMUNAL_HUB_SWIPE_TO_EXIT_START;
    public static final CommunalUiEvent COMMUNAL_HUB_WIDGET_PICKER_SHOWN;
    private final int id;

    static {
        CommunalUiEvent communalUiEvent = new CommunalUiEvent("COMMUNAL_HUB_SHOWN", 0, 1566);
        COMMUNAL_HUB_SHOWN = communalUiEvent;
        CommunalUiEvent communalUiEvent2 = new CommunalUiEvent("COMMUNAL_HUB_GONE", 1, 1577);
        COMMUNAL_HUB_GONE = communalUiEvent2;
        CommunalUiEvent communalUiEvent3 = new CommunalUiEvent("COMMUNAL_HUB_TIMEOUT", 2, 1578);
        CommunalUiEvent communalUiEvent4 = new CommunalUiEvent("COMMUNAL_HUB_LOADED", 3, 1579);
        CommunalUiEvent communalUiEvent5 = new CommunalUiEvent("COMMUNAL_HUB_SWIPE_TO_ENTER_START", 4, 1580);
        COMMUNAL_HUB_SWIPE_TO_ENTER_START = communalUiEvent5;
        CommunalUiEvent communalUiEvent6 = new CommunalUiEvent("COMMUNAL_HUB_SWIPE_TO_ENTER_FINISH", 5, 1581);
        COMMUNAL_HUB_SWIPE_TO_ENTER_FINISH = communalUiEvent6;
        CommunalUiEvent communalUiEvent7 = new CommunalUiEvent("COMMUNAL_HUB_SWIPE_TO_ENTER_CANCEL", 6, 1582);
        COMMUNAL_HUB_SWIPE_TO_ENTER_CANCEL = communalUiEvent7;
        CommunalUiEvent communalUiEvent8 = new CommunalUiEvent("COMMUNAL_HUB_SWIPE_TO_EXIT_START", 7, 1583);
        COMMUNAL_HUB_SWIPE_TO_EXIT_START = communalUiEvent8;
        CommunalUiEvent communalUiEvent9 = new CommunalUiEvent("COMMUNAL_HUB_SWIPE_TO_EXIT_FINISH", 8, 1584);
        COMMUNAL_HUB_SWIPE_TO_EXIT_FINISH = communalUiEvent9;
        CommunalUiEvent communalUiEvent10 = new CommunalUiEvent("COMMUNAL_HUB_SWIPE_TO_EXIT_CANCEL", 9, 1585);
        COMMUNAL_HUB_SWIPE_TO_EXIT_CANCEL = communalUiEvent10;
        CommunalUiEvent communalUiEvent11 = new CommunalUiEvent("COMMUNAL_HUB_REORDER_WIDGET_START", 10, 1586);
        COMMUNAL_HUB_REORDER_WIDGET_START = communalUiEvent11;
        CommunalUiEvent communalUiEvent12 = new CommunalUiEvent("COMMUNAL_HUB_REORDER_WIDGET_FINISH", 11, 1587);
        COMMUNAL_HUB_REORDER_WIDGET_FINISH = communalUiEvent12;
        CommunalUiEvent communalUiEvent13 = new CommunalUiEvent("COMMUNAL_HUB_REORDER_WIDGET_CANCEL", 12, 1588);
        COMMUNAL_HUB_REORDER_WIDGET_CANCEL = communalUiEvent13;
        CommunalUiEvent communalUiEvent14 = new CommunalUiEvent("COMMUNAL_HUB_EDIT_MODE_SHOWN", 13, 1569);
        COMMUNAL_HUB_EDIT_MODE_SHOWN = communalUiEvent14;
        CommunalUiEvent communalUiEvent15 = new CommunalUiEvent("COMMUNAL_HUB_EDIT_MODE_GONE", 14, 1589);
        COMMUNAL_HUB_EDIT_MODE_GONE = communalUiEvent15;
        CommunalUiEvent communalUiEvent16 = new CommunalUiEvent("COMMUNAL_HUB_WIDGET_PICKER_SHOWN", 15, 1590);
        COMMUNAL_HUB_WIDGET_PICKER_SHOWN = communalUiEvent16;
        CommunalUiEvent[] communalUiEventArr = {communalUiEvent, communalUiEvent2, communalUiEvent3, communalUiEvent4, communalUiEvent5, communalUiEvent6, communalUiEvent7, communalUiEvent8, communalUiEvent9, communalUiEvent10, communalUiEvent11, communalUiEvent12, communalUiEvent13, communalUiEvent14, communalUiEvent15, communalUiEvent16, new CommunalUiEvent("COMMUNAL_HUB_WIDGET_PICKER_GONE", 16, 1591), new CommunalUiEvent("COMMUNAL_HUB_SWIPE_UP_TO_BOUNCER", 17, 1573), new CommunalUiEvent("COMMUNAL_HUB_SWIPE_DOWN_TO_SHADE", 18, 1574)};
        $VALUES = communalUiEventArr;
        EnumEntriesKt.enumEntries(communalUiEventArr);
    }

    private CommunalUiEvent(String str, int i, int i2) {
        this.id = i2;
    }

    public static CommunalUiEvent valueOf(String str) {
        return (CommunalUiEvent) Enum.valueOf(CommunalUiEvent.class, str);
    }

    public static CommunalUiEvent[] values() {
        return (CommunalUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.id;
    }
}
