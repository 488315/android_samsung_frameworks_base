package com.android.wm.shell.bubbles;

import com.android.internal.logging.UiEventLogger;
import com.sec.ims.volte2.data.VolteConstants;

/* loaded from: classes3.dex */
public class BubbleLogger {
    public final UiEventLogger mUiEventLogger;

    public enum Event implements UiEventLogger.UiEventEnum {
        BUBBLE_OVERFLOW_ADD_USER_GESTURE(483),
        BUBBLE_OVERFLOW_ADD_AGED(VolteConstants.ErrorCode.ADDRESS_INCOMPLETE),
        BUBBLE_OVERFLOW_REMOVE_MAX_REACHED(485),
        BUBBLE_OVERFLOW_REMOVE_CANCEL(VolteConstants.ErrorCode.BUSY_HERE),
        BUBBLE_OVERFLOW_REMOVE_GROUP_CANCEL(VolteConstants.ErrorCode.REQUEST_TERMINATED),
        BUBBLE_OVERFLOW_REMOVE_NO_LONGER_BUBBLE(VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE),
        BUBBLE_OVERFLOW_REMOVE_BACK_TO_STACK(489),
        BUBBLE_OVERFLOW_REMOVE_BLOCKED(490),
        BUBBLE_OVERFLOW_SELECTED(VolteConstants.ErrorCode.BUSY_EVERYWHERE),
        BUBBLE_OVERFLOW_RECOVER(691),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_BUBBLE_POSTED(1927),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_BUBBLE_UPDATED(1928),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_EXPANDED(1929),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_COLLAPSED(1930),
        BUBBLE_BAR_BUBBLE_DISMISSED_DRAG_BUBBLE(1931),
        BUBBLE_BAR_BUBBLE_DISMISSED_DRAG_EXP_VIEW(1932),
        BUBBLE_BAR_BUBBLE_DISMISSED_APP_MENU(1933),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_APP_ICON_DROP(1934),
        BUBBLE_BAR_DISMISSED_DRAG_BAR(1935),
        BUBBLE_BAR_MOVED_LEFT_DRAG_EXP_VIEW(1936),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_RIGHT_APP_ICON_DROP(1937),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_APP_ICON_DROP(1938),
        BUBBLE_BAR_MOVED_RIGHT_DRAG_EXP_VIEW(1939),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_APP_ICON_DROP(1940),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_RIGHT_APP_ICON_DROP(1941),
        BUBBLE_BAR_APP_MENU_OPT_OUT(1942),
        BUBBLE_BAR_APP_MENU_GO_TO_SETTINGS(1943),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_APP_ICON_DROP(1944),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_RIGHT_APP_ICON_DROP(1945),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_APP_ICON_DROP(1946),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_RIGHT_APP_ICON_DROP(1947),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_APP_ICON_DROP(1948),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_RIGHT_APP_ICON_DROP(1949),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_APP_ICON_DROP(2082),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_RIGHT_APP_ICON_DROP(2083),
        BUBBLE_BAR_BUBBLE_SWITCHED(1977),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_LEFT_DRAG_TASK(2146),
        /* JADX INFO: Fake field, exist only in values array */
        BUBBLE_BAR_MOVED_RIGHT_DRAG_TASK(2147);

        public static final Event BUBBLE_BAR_BUBBLE_ACTIVITY_FINISH = null;
        public static final Event BUBBLE_BAR_BUBBLE_POSTED = null;
        public static final Event BUBBLE_BAR_BUBBLE_REMOVED_BLOCKED = null;
        public static final Event BUBBLE_BAR_BUBBLE_REMOVED_CANCELED = null;
        public static final Event BUBBLE_BAR_BUBBLE_UPDATED = null;
        public static final Event BUBBLE_BAR_COLLAPSED = null;
        public static final Event BUBBLE_BAR_EXPANDED = null;
        public static final Event BUBBLE_BAR_OVERFLOW_ADD_AGED = null;
        public static final Event BUBBLE_BAR_OVERFLOW_SELECTED = null;
        private final int mId;

        Event(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public BubbleLogger(UiEventLogger uiEventLogger) {
        this.mUiEventLogger = uiEventLogger;
    }

    public final void log(Bubble bubble, UiEventLogger.UiEventEnum uiEventEnum) {
        this.mUiEventLogger.logWithInstanceId(uiEventEnum, bubble.mAppUid, bubble.mPackageName, bubble.mInstanceId);
    }
}
