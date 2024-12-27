package com.android.systemui.clipboardoverlay;

import com.android.internal.logging.UiEventLogger;

public enum ClipboardOverlayEvent implements UiEventLogger.UiEventEnum {
    CLIPBOARD_OVERLAY_ENTERED(949),
    CLIPBOARD_OVERLAY_UPDATED(950),
    CLIPBOARD_OVERLAY_EDIT_TAPPED(951),
    CLIPBOARD_OVERLAY_SHARE_TAPPED(1067),
    CLIPBOARD_OVERLAY_ACTION_SHOWN(1260),
    CLIPBOARD_OVERLAY_ACTION_TAPPED(952),
    CLIPBOARD_OVERLAY_REMOTE_COPY_TAPPED(953),
    CLIPBOARD_OVERLAY_TIMED_OUT(954),
    CLIPBOARD_OVERLAY_DISMISS_TAPPED(955),
    CLIPBOARD_OVERLAY_SWIPE_DISMISSED(956),
    CLIPBOARD_OVERLAY_TAP_OUTSIDE(1077),
    CLIPBOARD_OVERLAY_DISMISSED_OTHER(1078),
    CLIPBOARD_OVERLAY_SHOWN_EXPANDED(1356),
    CLIPBOARD_OVERLAY_SHOWN_MINIMIZED(1357),
    CLIPBOARD_OVERLAY_EXPANDED_FROM_MINIMIZED(1358),
    CLIPBOARD_TOAST_SHOWN(1270);

    public static final ClipboardOverlayEvent CLIPBOARD_OVERLAY_EDIT_TAPPED = null;
    public static final ClipboardOverlayEvent CLIPBOARD_OVERLAY_SHARE_TAPPED = null;
    private final int mId;

    ClipboardOverlayEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
