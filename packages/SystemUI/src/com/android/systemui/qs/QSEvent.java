package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

public final class QSEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ QSEvent[] $VALUES;
    public static final QSEvent QQS_PANEL_COLLAPSED;
    public static final QSEvent QQS_PANEL_EXPANDED;
    public static final QSEvent QQS_TILE_VISIBLE;
    public static final QSEvent QS_ACTION_CLICK;
    public static final QSEvent QS_ACTION_LONG_PRESS;
    public static final QSEvent QS_ACTION_SECONDARY_CLICK;
    public static final QSEvent QS_PANEL_COLLAPSED;
    public static final QSEvent QS_PANEL_EXPANDED;
    public static final QSEvent QS_TILE_VISIBLE;
    private final int _id;

    static {
        QSEvent qSEvent = new QSEvent("QS_ACTION_CLICK", 0, 387);
        QS_ACTION_CLICK = qSEvent;
        QSEvent qSEvent2 = new QSEvent("QS_ACTION_SECONDARY_CLICK", 1, 388);
        QS_ACTION_SECONDARY_CLICK = qSEvent2;
        QSEvent qSEvent3 = new QSEvent("QS_ACTION_LONG_PRESS", 2, 389);
        QS_ACTION_LONG_PRESS = qSEvent3;
        QSEvent qSEvent4 = new QSEvent("QS_PANEL_EXPANDED", 3, 390);
        QS_PANEL_EXPANDED = qSEvent4;
        QSEvent qSEvent5 = new QSEvent("QS_PANEL_COLLAPSED", 4, 391);
        QS_PANEL_COLLAPSED = qSEvent5;
        QSEvent qSEvent6 = new QSEvent("QS_TILE_VISIBLE", 5, 392);
        QS_TILE_VISIBLE = qSEvent6;
        QSEvent qSEvent7 = new QSEvent("QQS_PANEL_EXPANDED", 6, 393);
        QQS_PANEL_EXPANDED = qSEvent7;
        QSEvent qSEvent8 = new QSEvent("QQS_PANEL_COLLAPSED", 7, 394);
        QQS_PANEL_COLLAPSED = qSEvent8;
        QSEvent qSEvent9 = new QSEvent("QQS_TILE_VISIBLE", 8, 395);
        QQS_TILE_VISIBLE = qSEvent9;
        QSEvent[] qSEventArr = {qSEvent, qSEvent2, qSEvent3, qSEvent4, qSEvent5, qSEvent6, qSEvent7, qSEvent8, qSEvent9};
        $VALUES = qSEventArr;
        EnumEntriesKt.enumEntries(qSEventArr);
    }

    private QSEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static QSEvent valueOf(String str) {
        return (QSEvent) Enum.valueOf(QSEvent.class, str);
    }

    public static QSEvent[] values() {
        return (QSEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}
