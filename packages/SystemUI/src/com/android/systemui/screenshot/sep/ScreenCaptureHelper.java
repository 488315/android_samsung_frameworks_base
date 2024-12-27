package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class ScreenCaptureHelper {
    public static final String TAG;
    public int builtInDisplayId;
    public Bundle captureSharedBundle;
    public int capturedDisplayId;
    public Context displayContext;
    public int displayHeight;
    public int displayRotation;
    public int displayWidth;
    public boolean isNavigationBarVisible;
    public boolean isStatusBarVisible;
    public Bundle mBundle;
    public int navigationBarHeight;
    public int safeInsetBottom;
    public int safeInsetLeft;
    public int safeInsetRight;
    public int safeInsetTop;
    public int screenCaptureOrigin;
    public int screenCaptureSweepDirection;
    public int screenCaptureType;
    public float screenDegrees;
    public int screenHeight;
    public float screenNativeHeight;
    public float screenNativeWidth;
    public int screenWidth;
    public Rect stackBounds;
    public int statusBarHeight;
    public int windowMode;
    public final DisplayInfo displayInfo = new DisplayInfo();
    public Rect rectToCapture = new Rect();

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
        TAG = "ScreenCaptureHelper";
    }

    public static float getDegreesForRotation(Display display) {
        int rotation = display.getRotation();
        if (rotation == 1) {
            return 270.0f;
        }
        if (rotation != 2) {
            return rotation != 3 ? 0.0f : 90.0f;
        }
        return 180.0f;
    }

    public final void getExcludeSystemUIRect() {
        int i;
        int i2;
        String str = "old rectToCapture : " + this.rectToCapture;
        String str2 = TAG;
        Log.d(str2, str);
        int i3 = 0;
        int i4 = this.isStatusBarVisible ? this.statusBarHeight : 0;
        int i5 = this.isNavigationBarVisible ? this.navigationBarHeight : 0;
        int navBarPosition = ScreenshotUtils.getNavBarPosition(this.displayContext, i5, false);
        if (navBarPosition == 2) {
            i = this.screenWidth - i5;
            i2 = this.screenHeight;
            this.screenWidth = i;
            this.screenHeight = i2 - i4;
        } else if (navBarPosition != 4) {
            int i6 = this.screenHeight;
            i = this.screenWidth;
            this.screenWidth = i - i5;
            this.screenHeight = i6 - i4;
            int i7 = i5;
            i2 = i6;
            i3 = i7;
        } else {
            i = this.screenWidth;
            int i8 = this.screenHeight;
            this.screenHeight = i8 - (i5 + i4);
            i2 = i8 - i5;
        }
        Rect rect = this.rectToCapture;
        Intrinsics.checkNotNull(rect);
        rect.set(i3, i4, i, i2);
        Log.i(str2, "new getExcludeSystemUIRect : " + this.rectToCapture + " navigationBarPosition : " + navBarPosition);
        Unit unit = Unit.INSTANCE;
    }

    public Rect getScreenshotEffectRect() {
        if (this.screenCaptureType == 1 && ScreenshotUtils.isExcludeSystemUI(this.displayContext)) {
            getExcludeSystemUIRect();
        }
        return this.rectToCapture;
    }

    public Rect getScreenshotRectToCapture() {
        if (this.screenCaptureType == 1 && ScreenshotUtils.isExcludeSystemUI(this.displayContext)) {
            getExcludeSystemUIRect();
        }
        return this.rectToCapture;
    }

    public void initializeCaptureType() {
        this.screenCaptureType = 1;
    }

    public void initializeScreenshotVariable() {
        Display display = ScreenshotUtils.getDisplay(this.capturedDisplayId, this.displayContext);
        display.getDisplayInfo(this.displayInfo);
        DisplayInfo displayInfo = this.displayInfo;
        int i = displayInfo.logicalWidth;
        this.displayWidth = i;
        this.screenWidth = i;
        int i2 = displayInfo.logicalHeight;
        this.displayHeight = i2;
        this.screenHeight = i2;
        this.screenNativeWidth = i;
        this.screenNativeHeight = i2;
        this.displayRotation = displayInfo.rotation;
        float degreesForRotation = getDegreesForRotation(display);
        this.screenDegrees = degreesForRotation;
        Log.d(TAG, "initializeScreenshotVariable: screenDegrees=" + degreesForRotation);
        if (this.screenDegrees > 0.0f) {
            float[] fArr = {this.screenNativeWidth, this.screenNativeHeight};
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.preRotate(-this.screenDegrees);
            matrix.mapPoints(fArr);
            fArr[0] = Math.abs(fArr[0]);
            float abs = Math.abs(fArr[1]);
            fArr[1] = abs;
            this.screenNativeWidth = fArr[0];
            this.screenNativeHeight = abs;
        }
        this.builtInDisplayId = this.capturedDisplayId;
    }

    public boolean isB5CoverScreenInReverseMode() {
        return false;
    }

    public boolean isB5ScreenEffect() {
        return false;
    }

    public boolean isLetterBoxHide() {
        return false;
    }

    public boolean isShowScreenshotAnimation() {
        return !(this instanceof ScreenCaptureHelperForPartial);
    }

    public final String toString() {
        int i = this.screenCaptureType;
        int i2 = this.screenCaptureSweepDirection;
        int i3 = this.capturedDisplayId;
        int i4 = this.screenCaptureOrigin;
        int i5 = this.safeInsetLeft;
        int i6 = this.safeInsetTop;
        int i7 = this.safeInsetRight;
        int i8 = this.safeInsetBottom;
        Bundle bundle = this.captureSharedBundle;
        int i9 = this.statusBarHeight;
        int i10 = this.navigationBarHeight;
        boolean z = this.isStatusBarVisible;
        boolean z2 = this.isNavigationBarVisible;
        Bundle bundle2 = this.mBundle;
        Context context = this.displayContext;
        DisplayInfo displayInfo = this.displayInfo;
        int i11 = this.displayWidth;
        int i12 = this.displayHeight;
        int i13 = this.screenWidth;
        int i14 = this.screenHeight;
        Rect rect = this.rectToCapture;
        float f = this.screenDegrees;
        float f2 = this.screenNativeWidth;
        float f3 = this.screenNativeHeight;
        int i15 = this.builtInDisplayId;
        Rect rect2 = this.stackBounds;
        int i16 = this.windowMode;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "ScreenCaptureHelper(screenCaptureType=", ", screenCaptureSweepDirection=", ", capturedDisplayId=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i3, ", screenCaptureOrigin=", i4, ", safeInsetLeft=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i5, ", safeInsetTop=", i6, ", safeInsetRight=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i7, ", safeInsetBottom=", i8, ", captureSharedBundle=");
        m.append(bundle);
        m.append(", statusBarHeight=");
        m.append(i9);
        m.append(", navigationBarHeight=");
        m.append(i10);
        m.append(", isStatusBarVisible=");
        m.append(z);
        m.append(", isNavigationBarVisible=");
        m.append(z2);
        m.append(", mBundle=");
        m.append(bundle2);
        m.append(", displayContext=");
        m.append(context);
        m.append(", displayInfo=");
        m.append(displayInfo);
        m.append(", displayWidth=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i11, ", displayHeight=", i12, ", screenWidth=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i13, ", screenHeight=", i14, ", rectToCapture=");
        m.append(rect);
        m.append(", screenDegrees=");
        m.append(f);
        m.append(", screenNativeWidth=");
        m.append(f2);
        m.append(", screenNativeHeight=");
        m.append(f3);
        m.append(", builtInDisplayId=");
        m.append(i15);
        m.append(", stackBounds=");
        m.append(rect2);
        m.append(", windowMode=");
        return Anchor$$ExternalSyntheticOutline0.m(i16, ")", m);
    }

    public Bitmap onPostScreenshot(Bitmap bitmap) {
        return bitmap;
    }
}
