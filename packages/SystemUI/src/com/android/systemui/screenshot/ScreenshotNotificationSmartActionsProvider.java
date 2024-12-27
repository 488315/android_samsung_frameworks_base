package com.android.systemui.screenshot;

public final class ScreenshotNotificationSmartActionsProvider {

    public enum ScreenshotOp {
        /* JADX INFO: Fake field, exist only in values array */
        OP_UNKNOWN,
        /* JADX INFO: Fake field, exist only in values array */
        RETRIEVE_SMART_ACTIONS,
        /* JADX INFO: Fake field, exist only in values array */
        REQUEST_SMART_ACTIONS,
        /* JADX INFO: Fake field, exist only in values array */
        WAIT_FOR_SMART_ACTIONS;

        public static final ScreenshotOp REQUEST_SMART_ACTIONS = null;
        public static final ScreenshotOp WAIT_FOR_SMART_ACTIONS = null;
    }

    public enum ScreenshotOpStatus {
        /* JADX INFO: Fake field, exist only in values array */
        OP_STATUS_UNKNOWN,
        /* JADX INFO: Fake field, exist only in values array */
        SUCCESS,
        /* JADX INFO: Fake field, exist only in values array */
        ERROR,
        /* JADX INFO: Fake field, exist only in values array */
        TIMEOUT;

        public static final ScreenshotOpStatus ERROR = null;
        public static final ScreenshotOpStatus SUCCESS = null;
        public static final ScreenshotOpStatus TIMEOUT = null;
    }

    public enum ScreenshotSmartActionType {
        /* JADX INFO: Fake field, exist only in values array */
        REGULAR_SMART_ACTIONS,
        /* JADX INFO: Fake field, exist only in values array */
        QUICK_SHARE_ACTION;

        public static final ScreenshotSmartActionType QUICK_SHARE_ACTION = null;
        public static final ScreenshotSmartActionType REGULAR_SMART_ACTIONS = null;
    }
}
