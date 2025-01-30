package com.android.systemui.stylus;

import com.android.internal.logging.UiEventLogger;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum StylusUiEvent implements UiEventLogger.UiEventEnum {
    STYLUS_LOW_BATTERY_NOTIFICATION_SHOWN(1298),
    STYLUS_LOW_BATTERY_NOTIFICATION_CLICKED(1299),
    STYLUS_LOW_BATTERY_NOTIFICATION_DISMISSED(1300),
    /* JADX INFO: Fake field, exist only in values array */
    STYLUS_STARTED_CHARGING(VolteConstants.ErrorCode.PPP_OPEN_FAILURE),
    /* JADX INFO: Fake field, exist only in values array */
    STYLUS_STOPPED_CHARGING(1303),
    BLUETOOTH_STYLUS_CONNECTED(1304),
    BLUETOOTH_STYLUS_DISCONNECTED(1305),
    USI_STYLUS_BATTERY_PRESENCE_FIRST_DETECTED(1306),
    USI_STYLUS_BATTERY_PRESENCE_REMOVED(1307);

    private final int _id;

    StylusUiEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}
