package com.android.systemui.screenrecord;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.custom.IKnoxCustomManager;

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
