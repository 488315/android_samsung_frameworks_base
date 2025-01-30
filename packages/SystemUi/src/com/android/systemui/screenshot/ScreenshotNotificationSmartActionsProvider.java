package com.android.systemui.screenshot;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotNotificationSmartActionsProvider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ScreenshotOp {
        /* JADX INFO: Fake field, exist only in values array */
        OP_UNKNOWN,
        /* JADX INFO: Fake field, exist only in values array */
        RETRIEVE_SMART_ACTIONS,
        REQUEST_SMART_ACTIONS,
        WAIT_FOR_SMART_ACTIONS
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ScreenshotOpStatus {
        /* JADX INFO: Fake field, exist only in values array */
        OP_STATUS_UNKNOWN,
        SUCCESS,
        ERROR,
        TIMEOUT
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ScreenshotSmartActionType {
        REGULAR_SMART_ACTIONS,
        QUICK_SHARE_ACTION
    }
}
