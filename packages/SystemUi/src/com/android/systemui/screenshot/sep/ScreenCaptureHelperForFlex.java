package com.android.systemui.screenshot.sep;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.DisplayInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenCaptureHelperForFlex extends ScreenCaptureHelper {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        int i;
        int i2;
        int i3;
        Rect rect;
        Display display = ScreenshotUtils.getDisplay(this.capturedDisplayId, this.displayContext);
        DisplayInfo displayInfo = this.displayInfo;
        display.getDisplayInfo(displayInfo);
        this.displayWidth = displayInfo.logicalWidth;
        this.displayHeight = displayInfo.logicalHeight;
        this.displayRotation = displayInfo.rotation;
        this.screenDegrees = ScreenCaptureHelper.getDegreesForRotation(display);
        Bundle bundle = this.captureSharedBundle;
        if (bundle != null && (rect = (Rect) bundle.getParcelable("rect")) != null) {
            this.rectToCapture = rect;
        }
        Rect rect2 = this.rectToCapture;
        Intrinsics.checkNotNull(rect2);
        this.screenWidth = rect2.width();
        Rect rect3 = this.rectToCapture;
        Intrinsics.checkNotNull(rect3);
        this.screenHeight = rect3.height();
        int i4 = 0;
        int i5 = (this.isStatusBarVisible && ScreenshotUtils.isExcludeSystemUI(this.displayContext)) ? this.statusBarHeight : 0;
        int i6 = (int) this.screenDegrees;
        if (i6 == 0) {
            Rect rect4 = this.rectToCapture;
            Intrinsics.checkNotNull(rect4);
            i4 = rect4.left;
            Rect rect5 = this.rectToCapture;
            Intrinsics.checkNotNull(rect5);
            i = i5 + rect5.top;
            Rect rect6 = this.rectToCapture;
            Intrinsics.checkNotNull(rect6);
            i2 = rect6.right;
            Rect rect7 = this.rectToCapture;
            Intrinsics.checkNotNull(rect7);
            i3 = rect7.bottom;
        } else if (i6 == 90) {
            Rect rect8 = this.rectToCapture;
            Intrinsics.checkNotNull(rect8);
            i4 = rect8.top + i5;
            int i7 = this.displayWidth;
            Rect rect9 = this.rectToCapture;
            Intrinsics.checkNotNull(rect9);
            i = i7 - rect9.right;
            Rect rect10 = this.rectToCapture;
            Intrinsics.checkNotNull(rect10);
            i2 = rect10.bottom;
            int i8 = this.displayWidth;
            Rect rect11 = this.rectToCapture;
            Intrinsics.checkNotNull(rect11);
            i3 = i8 - rect11.left;
        } else if (i6 == 180) {
            int i9 = this.displayWidth;
            Rect rect12 = this.rectToCapture;
            Intrinsics.checkNotNull(rect12);
            i4 = i9 - rect12.right;
            int i10 = this.displayHeight;
            Rect rect13 = this.rectToCapture;
            Intrinsics.checkNotNull(rect13);
            int i11 = i10 - rect13.bottom;
            int i12 = this.displayWidth;
            Rect rect14 = this.rectToCapture;
            Intrinsics.checkNotNull(rect14);
            int i13 = i12 - rect14.left;
            int i14 = this.displayHeight;
            Rect rect15 = this.rectToCapture;
            Intrinsics.checkNotNull(rect15);
            i3 = (i14 - rect15.top) - i5;
            i = i11;
            i2 = i13;
        } else if (i6 != 270) {
            i = 0;
            i2 = 0;
            i3 = 0;
        } else {
            int i15 = this.displayHeight;
            Rect rect16 = this.rectToCapture;
            Intrinsics.checkNotNull(rect16);
            i4 = i15 - rect16.bottom;
            Rect rect17 = this.rectToCapture;
            Intrinsics.checkNotNull(rect17);
            int i16 = rect17.left;
            int i17 = this.displayHeight;
            Rect rect18 = this.rectToCapture;
            Intrinsics.checkNotNull(rect18);
            int i18 = (i17 - rect18.top) - i5;
            Rect rect19 = this.rectToCapture;
            Intrinsics.checkNotNull(rect19);
            i3 = rect19.right;
            i2 = i18;
            i = i16;
        }
        Rect rect20 = this.rectToCapture;
        Intrinsics.checkNotNull(rect20);
        rect20.set(i4, i, i2, i3);
        Intrinsics.checkNotNull(this.rectToCapture);
        this.screenNativeWidth = r0.width();
        Intrinsics.checkNotNull(this.rectToCapture);
        this.screenNativeHeight = r0.height();
        this.builtInDisplayId = this.capturedDisplayId;
    }
}
