package com.android.systemui.screenshot;

import com.android.systemui.screenshot.TakeScreenshotService;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public interface ScreenshotHandler {
    void handleScreenshot(ScreenshotData screenshotData, Consumer consumer, TakeScreenshotService.RequestCallback requestCallback);
}
