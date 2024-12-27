package com.android.systemui.screenshot.appclips;

import com.android.internal.logging.UiEventLogger;

enum AppClipsEvent implements UiEventLogger.UiEventEnum {
    SCREENSHOT_FOR_NOTE_TRIGGERED(1308),
    SCREENSHOT_FOR_NOTE_ACCEPTED(1309),
    SCREENSHOT_FOR_NOTE_CANCELLED(1310);

    private final int mId;

    AppClipsEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
