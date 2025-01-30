package com.android.systemui.screenshot.appclips;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
