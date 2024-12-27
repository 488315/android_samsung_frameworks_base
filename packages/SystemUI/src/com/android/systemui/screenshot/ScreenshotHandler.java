package com.android.systemui.screenshot;

import com.android.systemui.screenshot.TakeScreenshotService;
import java.util.function.Consumer;

public interface ScreenshotHandler {
    void handleScreenshot(ScreenshotData screenshotData, Consumer consumer, TakeScreenshotService.RequestCallback requestCallback);
}
