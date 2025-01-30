package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum QSEditEvent implements UiEventLogger.UiEventEnum {
    QS_EDIT_REMOVE(210),
    QS_EDIT_ADD(IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState),
    QS_EDIT_MOVE(IKnoxCustomManager.Stub.TRANSACTION_getWifiState),
    /* JADX INFO: Fake field, exist only in values array */
    QS_EDIT_OPEN(IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber),
    QS_EDIT_CLOSED(IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber),
    QS_EDIT_RESET(IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberDelay);

    private final int _id;

    QSEditEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}
