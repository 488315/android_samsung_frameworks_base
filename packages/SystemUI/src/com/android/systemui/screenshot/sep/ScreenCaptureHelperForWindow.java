package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.DisplayInfo;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForWindow extends ScreenCaptureHelper {

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
        bundle.getClass();
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
        rect2.getClass();
        this.screenHeight = rect2.height();
        float f = this.screenDegrees;
        if (f > 0.0f) {
            if (f == 90.0f) {
                int i6 = this.displayWidth;
                int i7 = i6 - i4;
                int i8 = i6 - i;
                Rect rect3 = this.rectToCapture;
                rect3.getClass();
                rect3.set(i3, i7, i5, i8);
            } else if (f == 270.0f) {
                int i9 = this.displayHeight;
                int i10 = i9 - i5;
                int i11 = i9 - i3;
                Rect rect4 = this.rectToCapture;
                rect4.getClass();
                rect4.set(i10, i, i11, i4);
            }
        }
        this.rectToCapture.getClass();
        this.screenNativeWidth = r1.width();
        this.rectToCapture.getClass();
        this.screenNativeHeight = r1.height();
        this.builtInDisplayId = this.capturedDisplayId;
        this.windowMode = i2;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final boolean isShowScreenshotAnimation(Context context) {
        return false;
    }
}
