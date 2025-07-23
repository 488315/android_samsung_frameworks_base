package com.android.systemui.screenshot.appclips;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
