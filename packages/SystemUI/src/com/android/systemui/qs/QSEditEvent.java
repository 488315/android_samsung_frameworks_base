package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class QSEditEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ QSEditEvent[] $VALUES;
    public static final QSEditEvent QS_EDIT_ADD;
    public static final QSEditEvent QS_EDIT_MOVE;
    public static final QSEditEvent QS_EDIT_REMOVE;
    public static final QSEditEvent QS_EDIT_RESET;
    private final int _id;

    static {
        QSEditEvent qSEditEvent = new QSEditEvent("QS_EDIT_REMOVE", 0, 210);
        QS_EDIT_REMOVE = qSEditEvent;
        QSEditEvent qSEditEvent2 = new QSEditEvent("QS_EDIT_ADD", 1, IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState);
        QS_EDIT_ADD = qSEditEvent2;
        QSEditEvent qSEditEvent3 = new QSEditEvent("QS_EDIT_MOVE", 2, IKnoxCustomManager.Stub.TRANSACTION_getWifiState);
        QS_EDIT_MOVE = qSEditEvent3;
        QSEditEvent qSEditEvent4 = new QSEditEvent("QS_EDIT_OPEN", 3, IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber);
        QSEditEvent qSEditEvent5 = new QSEditEvent("QS_EDIT_CLOSED", 4, IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber);
        QSEditEvent qSEditEvent6 = new QSEditEvent("QS_EDIT_RESET", 5, IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberDelay);
        QS_EDIT_RESET = qSEditEvent6;
        QSEditEvent[] qSEditEventArr = {qSEditEvent, qSEditEvent2, qSEditEvent3, qSEditEvent4, qSEditEvent5, qSEditEvent6};
        $VALUES = qSEditEventArr;
        EnumEntriesKt.enumEntries(qSEditEventArr);
    }

    private QSEditEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static QSEditEvent valueOf(String str) {
        return (QSEditEvent) Enum.valueOf(QSEditEvent.class, str);
    }

    public static QSEditEvent[] values() {
        return (QSEditEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this._id;
    }
}
