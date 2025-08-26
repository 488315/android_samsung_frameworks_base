package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class QSModesEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ QSModesEvent[] $VALUES;
    public static final QSModesEvent QS_MODES_DND_OFF;
    public static final QSModesEvent QS_MODES_DND_ON;
    public static final QSModesEvent QS_MODES_DND_SETTINGS;
    public static final QSModesEvent QS_MODES_DURATION_DIALOG;
    public static final QSModesEvent QS_MODES_MODE_OFF;
    public static final QSModesEvent QS_MODES_MODE_ON;
    public static final QSModesEvent QS_MODES_MODE_SETTINGS;
    public static final QSModesEvent QS_MODES_SETTINGS;
    private final int _id;

    static {
        QSModesEvent qSModesEvent = new QSModesEvent("QS_MODES_DND_ON", 0, 1870);
        QS_MODES_DND_ON = qSModesEvent;
        QSModesEvent qSModesEvent2 = new QSModesEvent("QS_MODES_DND_OFF", 1, 1871);
        QS_MODES_DND_OFF = qSModesEvent2;
        QSModesEvent qSModesEvent3 = new QSModesEvent("QS_MODES_DND_SETTINGS", 2, 1872);
        QS_MODES_DND_SETTINGS = qSModesEvent3;
        QSModesEvent qSModesEvent4 = new QSModesEvent("QS_MODES_MODE_ON", 3, 1873);
        QS_MODES_MODE_ON = qSModesEvent4;
        QSModesEvent qSModesEvent5 = new QSModesEvent("QS_MODES_MODE_OFF", 4, 1874);
        QS_MODES_MODE_OFF = qSModesEvent5;
        QSModesEvent qSModesEvent6 = new QSModesEvent("QS_MODES_MODE_SETTINGS", 5, 1875);
        QS_MODES_MODE_SETTINGS = qSModesEvent6;
        QSModesEvent qSModesEvent7 = new QSModesEvent("QS_MODES_SETTINGS", 6, 1876);
        QS_MODES_SETTINGS = qSModesEvent7;
        QSModesEvent qSModesEvent8 = new QSModesEvent("QS_MODES_DURATION_DIALOG", 7, 1879);
        QS_MODES_DURATION_DIALOG = qSModesEvent8;
        QSModesEvent[] qSModesEventArr = {qSModesEvent, qSModesEvent2, qSModesEvent3, qSModesEvent4, qSModesEvent5, qSModesEvent6, qSModesEvent7, qSModesEvent8};
        $VALUES = qSModesEventArr;
        EnumEntriesKt.enumEntries(qSModesEventArr);
    }

    private QSModesEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static QSModesEvent valueOf(String str) {
        return (QSModesEvent) Enum.valueOf(QSModesEvent.class, str);
    }

    public static QSModesEvent[] values() {
        return (QSModesEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}
