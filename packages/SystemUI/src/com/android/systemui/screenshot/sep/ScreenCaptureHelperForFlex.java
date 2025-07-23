package com.android.systemui.screenshot.sep;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.DisplayInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForFlex extends ScreenCaptureHelper {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final void initializeCaptureType() {
        this.screenCaptureType = 101;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final void initializeScreenshotVariable() {
        Rect rect;
        Display display = ScreenshotUtils.getDisplay(this.capturedDisplayId, this.displayContext);
        display.getDisplayInfo(this.displayInfo);
        DisplayInfo displayInfo = this.displayInfo;
        this.displayWidth = displayInfo.logicalWidth;
        this.displayHeight = displayInfo.logicalHeight;
        this.displayRotation = displayInfo.rotation;
        this.screenDegrees = ScreenCaptureHelper.getDegreesForRotation(display);
        Bundle bundle = this.captureSharedBundle;
        if (bundle != null && (rect = (Rect) bundle.getParcelable("rect")) != null) {
            this.rectToCapture = rect;
        }
        if (this.isStatusBarVisible && ScreenshotUtils.isExcludeSystemUI(this.displayContext)) {
            Rect rect2 = this.rectToCapture;
            rect2.getClass();
            rect2.top += this.statusBarHeight;
        }
        Rect rect3 = this.rectToCapture;
        rect3.getClass();
        this.screenWidth = rect3.width();
        Rect rect4 = this.rectToCapture;
        rect4.getClass();
        this.screenHeight = rect4.height();
        this.builtInDisplayId = this.capturedDisplayId;
    }
}
