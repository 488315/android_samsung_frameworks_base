package com.android.systemui.screenshot;

import com.android.systemui.screenshot.TakeScreenshotService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ScreenshotHandler {
    void handleScreenshot(ScreenshotData screenshotData, Consumer consumer, TakeScreenshotService.RequestCallback requestCallback);
}
