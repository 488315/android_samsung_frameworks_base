package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSDndEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ QSDndEvent[] $VALUES;
    public static final QSDndEvent QS_DND_CONDITION_SELECT;
    public static final QSDndEvent QS_DND_DIALOG_ENABLE_FOREVER;
    public static final QSDndEvent QS_DND_DIALOG_ENABLE_UNTIL_ALARM;
    public static final QSDndEvent QS_DND_DIALOG_ENABLE_UNTIL_COUNTDOWN;
    public static final QSDndEvent QS_DND_TIME_DOWN;
    public static final QSDndEvent QS_DND_TIME_UP;
    private final int _id;

    static {
        QSDndEvent qSDndEvent = new QSDndEvent("QS_DND_CONDITION_SELECT", 0, VolteConstants.ErrorCode.BAD_EXTENSION);
        QS_DND_CONDITION_SELECT = qSDndEvent;
        QSDndEvent qSDndEvent2 = new QSDndEvent("QS_DND_TIME_UP", 1, VolteConstants.ErrorCode.SESSION_INTERVAL_TOO_SMALL);
        QS_DND_TIME_UP = qSDndEvent2;
        QSDndEvent qSDndEvent3 = new QSDndEvent("QS_DND_TIME_DOWN", 2, VolteConstants.ErrorCode.INTERVAL_TOO_BRIEF);
        QS_DND_TIME_DOWN = qSDndEvent3;
        QSDndEvent qSDndEvent4 = new QSDndEvent("QS_DND_DIALOG_ENABLE_FOREVER", 3, 946);
        QS_DND_DIALOG_ENABLE_FOREVER = qSDndEvent4;
        QSDndEvent qSDndEvent5 = new QSDndEvent("QS_DND_DIALOG_ENABLE_UNTIL_ALARM", 4, 947);
        QS_DND_DIALOG_ENABLE_UNTIL_ALARM = qSDndEvent5;
        QSDndEvent qSDndEvent6 = new QSDndEvent("QS_DND_DIALOG_ENABLE_UNTIL_COUNTDOWN", 5, 948);
        QS_DND_DIALOG_ENABLE_UNTIL_COUNTDOWN = qSDndEvent6;
        QSDndEvent[] qSDndEventArr = {qSDndEvent, qSDndEvent2, qSDndEvent3, qSDndEvent4, qSDndEvent5, qSDndEvent6};
        $VALUES = qSDndEventArr;
        EnumEntriesKt.enumEntries(qSDndEventArr);
    }

    private QSDndEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static QSDndEvent valueOf(String str) {
        return (QSDndEvent) Enum.valueOf(QSDndEvent.class, str);
    }

    public static QSDndEvent[] values() {
        return (QSDndEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}
