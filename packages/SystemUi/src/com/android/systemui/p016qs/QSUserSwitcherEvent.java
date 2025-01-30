package com.android.systemui.p016qs;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum QSUserSwitcherEvent implements UiEventLogger.UiEventEnum {
    QS_USER_SWITCH(424),
    QS_USER_DETAIL_OPEN(425),
    QS_USER_DETAIL_CLOSE(426),
    QS_USER_MORE_SETTINGS(427),
    QS_USER_GUEST_ADD(754),
    QS_USER_GUEST_WIPE(755),
    QS_USER_GUEST_CONTINUE(756),
    QS_USER_GUEST_REMOVE(757);

    private final int _id;

    QSUserSwitcherEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}
