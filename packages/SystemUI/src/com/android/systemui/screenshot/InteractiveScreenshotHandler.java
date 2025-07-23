package com.android.systemui.screenshot;

import android.os.Bundle;
import android.view.Display;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface InteractiveScreenshotHandler extends ScreenshotHandler {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
