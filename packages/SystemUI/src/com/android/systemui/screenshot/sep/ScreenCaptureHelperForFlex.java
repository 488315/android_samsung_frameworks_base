package com.android.systemui.screenshot.sep;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.DisplayInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForFlex extends ScreenCaptureHelper {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            Intrinsics.checkNotNull(rect2);
            rect2.top += this.statusBarHeight;
        }
        Rect rect3 = this.rectToCapture;
        Intrinsics.checkNotNull(rect3);
        this.screenWidth = rect3.width();
        Rect rect4 = this.rectToCapture;
        Intrinsics.checkNotNull(rect4);
        this.screenHeight = rect4.height();
        this.builtInDisplayId = this.capturedDisplayId;
    }
}
