package com.android.systemui.screenshot.sep;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.DisplayInfo;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenCaptureHelperForWindow extends ScreenCaptureHelper {

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
        this.screenCaptureType = 100;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final void initializeScreenshotVariable() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Display display = ScreenshotUtils.getDisplay(this.capturedDisplayId, this.displayContext);
        display.getDisplayInfo(this.displayInfo);
        DisplayInfo displayInfo = this.displayInfo;
        this.displayWidth = displayInfo.logicalWidth;
        this.displayHeight = displayInfo.logicalHeight;
        this.displayRotation = displayInfo.rotation;
        this.screenDegrees = ScreenCaptureHelper.getDegreesForRotation(display);
        Bundle bundle = this.mBundle;
        Intrinsics.checkNotNull(bundle);
        ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("windowCapture");
        if (integerArrayList != null) {
            i = integerArrayList.get(0).intValue();
            i3 = integerArrayList.get(1).intValue();
            i4 = integerArrayList.get(2).intValue();
            i5 = integerArrayList.get(3).intValue();
            i2 = integerArrayList.get(4).intValue();
        } else {
            i = -1;
            i2 = 1;
            i3 = -1;
            i4 = -1;
            i5 = -1;
        }
        Rect rect = new Rect(Math.max(0, i), Math.max(0, i3), Math.min(i4, this.displayWidth), Math.min(i5, this.displayHeight));
        this.rectToCapture = rect;
        this.screenWidth = rect.width();
        Rect rect2 = this.rectToCapture;
        Intrinsics.checkNotNull(rect2);
        this.screenHeight = rect2.height();
        float f = this.screenDegrees;
        if (f > 0.0f) {
            if (f == 90.0f) {
                int i6 = this.displayWidth;
                int i7 = i6 - i4;
                int i8 = i6 - i;
                Rect rect3 = this.rectToCapture;
                Intrinsics.checkNotNull(rect3);
                rect3.set(i3, i7, i5, i8);
            } else if (f == 270.0f) {
                int i9 = this.displayHeight;
                int i10 = i9 - i5;
                int i11 = i9 - i3;
                Rect rect4 = this.rectToCapture;
                Intrinsics.checkNotNull(rect4);
                rect4.set(i10, i, i11, i4);
            }
        }
        Intrinsics.checkNotNull(this.rectToCapture);
        this.screenNativeWidth = r1.width();
        Intrinsics.checkNotNull(this.rectToCapture);
        this.screenNativeHeight = r1.height();
        this.builtInDisplayId = this.capturedDisplayId;
        this.windowMode = i2;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final boolean isShowScreenshotAnimation() {
        return false;
    }
}
