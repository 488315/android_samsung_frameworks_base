package com.android.systemui.screenrecord;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public enum Events$ScreenRecordEvent implements UiEventLogger.UiEventEnum {
    SCREEN_RECORD_START(IKnoxCustomManager.Stub.TRANSACTION_migrateApplicationRestrictions),
    SCREEN_RECORD_END_QS_TILE(300),
    SCREEN_RECORD_END_NOTIFICATION(301);

    private final int mId;

    Events$ScreenRecordEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
