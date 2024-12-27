package com.android.systemui.screenshot.scroll;

import java.util.concurrent.atomic.AtomicReference;

public final class LongScreenshotData {
    public final AtomicReference mLongScreenshot = new AtomicReference();

    public LongScreenshotData() {
        new AtomicReference();
    }
}
