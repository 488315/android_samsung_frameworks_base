package com.android.systemui.screenshot;

import com.android.systemui.screenshot.TakeScreenshotService;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ScreenshotHandler {
    void handleScreenshot(ScreenshotData screenshotData, Consumer consumer, TakeScreenshotService.RequestCallback requestCallback);
}
