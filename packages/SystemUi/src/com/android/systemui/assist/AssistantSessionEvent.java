package com.android.systemui.assist;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum AssistantSessionEvent implements UiEventLogger.UiEventEnum {
    /* JADX INFO: Fake field, exist only in values array */
    ASSISTANT_SESSION_UNKNOWN(0),
    /* JADX INFO: Fake field, exist only in values array */
    ASSISTANT_SESSION_TIMEOUT_DISMISS(524),
    /* JADX INFO: Fake field, exist only in values array */
    ASSISTANT_SESSION_INVOCATION_START(525),
    ASSISTANT_SESSION_INVOCATION_CANCELLED(526),
    /* JADX INFO: Fake field, exist only in values array */
    ASSISTANT_SESSION_USER_DISMISS(527),
    ASSISTANT_SESSION_UPDATE(528),
    ASSISTANT_SESSION_CLOSE(529);


    /* renamed from: id */
    private final int f230id;

    AssistantSessionEvent(int i) {
        this.f230id = i;
    }

    public final int getId() {
        return this.f230id;
    }
}
