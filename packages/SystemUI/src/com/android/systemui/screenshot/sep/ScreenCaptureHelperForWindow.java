package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.DisplayInfo;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForWindow extends ScreenCaptureHelper {

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
        int iIntValue;
        int iIntValue2;
        int iIntValue3;
        int iIntValue4;
        int iIntValue5;
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
            iIntValue = integerArrayList.get(0).intValue();
            iIntValue3 = integerArrayList.get(1).intValue();
            iIntValue4 = integerArrayList.get(2).intValue();
            iIntValue5 = integerArrayList.get(3).intValue();
            iIntValue2 = integerArrayList.get(4).intValue();
        } else {
            iIntValue = -1;
            iIntValue2 = 1;
            iIntValue3 = -1;
            iIntValue4 = -1;
            iIntValue5 = -1;
        }
        Rect rect = new Rect(Math.max(0, iIntValue), Math.max(0, iIntValue3), Math.min(iIntValue4, this.displayWidth), Math.min(iIntValue5, this.displayHeight));
        this.rectToCapture = rect;
        this.screenWidth = rect.width();
        Rect rect2 = this.rectToCapture;
        rect2.getClass();
        this.screenHeight = rect2.height();
        this.rectToCapture.getClass();
        this.screenNativeWidth = r1.width();
        this.rectToCapture.getClass();
        this.screenNativeHeight = r1.height();
        this.builtInDisplayId = this.capturedDisplayId;
        this.windowMode = iIntValue2;
    }

    @Override // com.android.systemui.screenshot.sep.ScreenCaptureHelper
    public final boolean isShowScreenshotAnimation(Context context) {
        return false;
    }
}
