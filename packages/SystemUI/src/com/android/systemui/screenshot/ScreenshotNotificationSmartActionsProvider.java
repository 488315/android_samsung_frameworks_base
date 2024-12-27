package com.android.systemui.screenshot;

public final class ScreenshotNotificationSmartActionsProvider {

    public enum ScreenshotOp {
        OP_UNKNOWN,
        RETRIEVE_SMART_ACTIONS,
        REQUEST_SMART_ACTIONS,
        WAIT_FOR_SMART_ACTIONS;

        public static final ScreenshotOp REQUEST_SMART_ACTIONS = null;
        public static final ScreenshotOp WAIT_FOR_SMART_ACTIONS = null;
    }

    public enum ScreenshotOpStatus {
        OP_STATUS_UNKNOWN,
        SUCCESS,
        ERROR,
        TIMEOUT;

        public static final ScreenshotOpStatus ERROR = null;
        public static final ScreenshotOpStatus SUCCESS = null;
        public static final ScreenshotOpStatus TIMEOUT = null;
    }

    public enum ScreenshotSmartActionType {
        REGULAR_SMART_ACTIONS,
        QUICK_SHARE_ACTION;

        public static final ScreenshotSmartActionType QUICK_SHARE_ACTION = null;
        public static final ScreenshotSmartActionType REGULAR_SMART_ACTIONS = null;
    }
}
