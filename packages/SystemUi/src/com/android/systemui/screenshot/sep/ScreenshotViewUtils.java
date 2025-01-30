package com.android.systemui.screenshot.sep;

import android.graphics.Rect;
import android.view.WindowManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotViewUtils {
    static {
        new ScreenshotViewUtils();
    }

    private ScreenshotViewUtils() {
    }

    public static final WindowManager.LayoutParams getLayoutParams(ScreenCaptureHelper screenCaptureHelper) {
        WindowManager.LayoutParams layoutParams;
        int animationWindowType = screenCaptureHelper.getAnimationWindowType();
        if (screenCaptureHelper instanceof ScreenCaptureHelperForFlex) {
            Rect screenshotRectToCapture = screenCaptureHelper.getScreenshotRectToCapture();
            if (screenCaptureHelper.screenDegrees == 0.0f) {
                Intrinsics.checkNotNull(screenshotRectToCapture);
                layoutParams = new WindowManager.LayoutParams(screenshotRectToCapture.width(), screenshotRectToCapture.height(), animationWindowType, 69207432, -3);
            } else {
                Intrinsics.checkNotNull(screenshotRectToCapture);
                layoutParams = new WindowManager.LayoutParams(screenshotRectToCapture.height(), screenshotRectToCapture.width(), animationWindowType, 69207432, -3);
            }
            layoutParams.gravity = 48;
        } else {
            layoutParams = new WindowManager.LayoutParams(-1, -1, animationWindowType, 69207432, -3);
            layoutParams.gravity = 17;
        }
        layoutParams.screenOrientation = -1;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(0);
        return layoutParams;
    }
}
