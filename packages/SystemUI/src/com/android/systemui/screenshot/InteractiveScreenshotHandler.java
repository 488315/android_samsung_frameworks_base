package com.android.systemui.screenshot;

import android.os.Bundle;
import android.view.Display;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;

/* loaded from: classes2.dex */
public interface InteractiveScreenshotHandler extends ScreenshotHandler {

    public interface Factory {
        InteractiveScreenshotHandler create(Display display);
    }

    void initSemScreenshotLayout();

    boolean isAnimationRunning();

    boolean isPendingSharedTransition();

    boolean isScreenshotSelectorViewVisible();

    boolean isSnackBarShowing();

    void onDestroy();

    void removeWindow();

    void requestDismissal(ScreenshotEvent screenshotEvent);

    void setPartialScreenshotSelector(Bundle bundle, ScreenshotData screenshotData, TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0 takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0, TakeScreenshotService.RequestCallback requestCallback);

    void setScreenCaptureHelper(ScreenCaptureHelper screenCaptureHelper);
}
