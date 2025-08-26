package com.android.systemui.screenshot.sep;

import android.graphics.Rect;
import android.view.WindowManager;
import com.sec.ims.volte2.data.VolteConstants;

/* loaded from: classes2.dex */
public final class ScreenshotViewUtils {
    static {
        new ScreenshotViewUtils();
    }

    private ScreenshotViewUtils() {
    }

    public static final WindowManager.LayoutParams getLayoutParams(ScreenCaptureHelper screenCaptureHelper) {
        WindowManager.LayoutParams layoutParams;
        screenCaptureHelper.getClass();
        if (screenCaptureHelper instanceof ScreenCaptureHelperForFlex) {
            Rect screenshotRectToCapture = screenCaptureHelper.getScreenshotRectToCapture();
            screenshotRectToCapture.getClass();
            layoutParams = new WindowManager.LayoutParams(screenshotRectToCapture.width(), screenshotRectToCapture.height(), VolteConstants.ErrorCode.REG_SUBSCRIBED, 69207432, -3);
            layoutParams.gravity = 48;
        } else {
            layoutParams = new WindowManager.LayoutParams(-1, -1, VolteConstants.ErrorCode.REG_SUBSCRIBED, 69207432, -3);
            layoutParams.gravity = 17;
        }
        layoutParams.screenOrientation = -1;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(0);
        return layoutParams;
    }
}
