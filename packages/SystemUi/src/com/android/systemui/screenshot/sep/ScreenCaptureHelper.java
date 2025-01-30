package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        TAG = "ScreenCaptureHelper";
    }

    public static float getDegreesForRotation(Display display) {
        int realRotation = display.getRealRotation();
        if (realRotation == 1) {
            return 270.0f;
        }
        if (realRotation != 2) {
            return realRotation != 3 ? 0.0f : 90.0f;
        }
        return 180.0f;
    }

    public int getAnimationWindowType() {
        return VolteConstants.ErrorCode.REG_SUBSCRIBED;
    }

    public final void getExcludeSystemUIRect() {
        int i;
        int i2;
        int i3;
        String str = "old rectToCapture : " + this.rectToCapture;
        String str2 = TAG;
        Log.d(str2, str);
        int i4 = 0;
        int i5 = this.isStatusBarVisible ? this.statusBarHeight : 0;
        int i6 = this.isNavigationBarVisible ? this.navigationBarHeight : 0;
        int navBarPosition = ScreenshotUtils.getNavBarPosition(this.displayContext, i6, false);
        this.statusBarHeight = 0;
        this.navigationBarHeight = 0;
        int i7 = (int) this.screenDegrees;
        if (i7 != 0) {
            if (i7 == 90) {
                if (navBarPosition == 4) {
                    float f = this.screenNativeWidth;
                    i3 = ((int) f) - i6;
                    i2 = (int) this.screenNativeHeight;
                    this.screenNativeWidth = f - (i6 + i5);
                } else {
                    float f2 = this.screenNativeWidth;
                    i3 = (int) f2;
                    float f3 = this.screenNativeHeight;
                    this.screenNativeWidth = f2 - i5;
                    this.screenNativeHeight = f3 - i6;
                    i2 = ((int) f3) - i6;
                }
                i4 = i5;
                i5 = 0;
            } else if (i7 == 180) {
                i = (int) this.screenNativeWidth;
                float f4 = this.screenNativeHeight;
                i2 = ((int) f4) - i5;
                this.screenNativeHeight = f4 - (i5 + i6);
                i5 = i6;
            } else if (i7 != 270) {
                i5 = 0;
                i3 = 0;
                i2 = 0;
            } else if (navBarPosition == 4) {
                float f5 = this.screenNativeWidth;
                i3 = ((int) f5) - i5;
                i2 = (int) this.screenNativeHeight;
                this.screenNativeWidth = f5 - (i5 + i6);
                i5 = 0;
                i4 = i6;
            } else {
                float f6 = this.screenNativeWidth;
                i3 = ((int) f6) - i5;
                float f7 = this.screenNativeHeight;
                this.screenNativeWidth = f6 - i5;
                this.screenNativeHeight = f7 - i6;
                i5 = 0;
                i2 = ((int) f7) - i6;
            }
            Rect rect = this.rectToCapture;
            Intrinsics.checkNotNull(rect);
            rect.set(i4, i5, i3, i2);
            Log.i(str2, "new getExcludeSystemUIRect : " + this.rectToCapture + " navigationBarPosition : " + navBarPosition);
            Unit unit = Unit.INSTANCE;
        }
        i = (int) this.screenNativeWidth;
        float f8 = this.screenNativeHeight;
        i2 = ((int) f8) - i6;
        this.screenNativeHeight = f8 - (i6 + i5);
        i3 = i;
        Rect rect2 = this.rectToCapture;
        Intrinsics.checkNotNull(rect2);
        rect2.set(i4, i5, i3, i2);
        Log.i(str2, "new getExcludeSystemUIRect : " + this.rectToCapture + " navigationBarPosition : " + navBarPosition);
        Unit unit2 = Unit.INSTANCE;
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
        DisplayInfo displayInfo = this.displayInfo;
        display.getDisplayInfo(displayInfo);
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
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("ScreenCaptureHelper(screenCaptureType=", i, ", screenCaptureSweepDirection=", i2, ", capturedDisplayId=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i3, ", screenCaptureOrigin=", i4, ", safeInsetLeft=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i5, ", safeInsetTop=", i6, ", safeInsetRight=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i7, ", safeInsetBottom=", i8, ", captureSharedBundle=");
        m45m.append(bundle);
        m45m.append(", statusBarHeight=");
        m45m.append(i9);
        m45m.append(", navigationBarHeight=");
        m45m.append(i10);
        m45m.append(", isStatusBarVisible=");
        m45m.append(z);
        m45m.append(", isNavigationBarVisible=");
        m45m.append(z2);
        m45m.append(", mBundle=");
        m45m.append(bundle2);
        m45m.append(", displayContext=");
        m45m.append(context);
        m45m.append(", displayInfo=");
        m45m.append(this.displayInfo);
        m45m.append(", displayWidth=");
        m45m.append(i11);
        m45m.append(", displayHeight=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i12, ", screenWidth=", i13, ", screenHeight=");
        m45m.append(i14);
        m45m.append(", rectToCapture=");
        m45m.append(rect);
        m45m.append(", screenDegrees=");
        m45m.append(f);
        m45m.append(", screenNativeWidth=");
        m45m.append(f2);
        m45m.append(", screenNativeHeight=");
        m45m.append(f3);
        m45m.append(", builtInDisplayId=");
        m45m.append(i15);
        m45m.append(", stackBounds=");
        m45m.append(rect2);
        m45m.append(", windowMode=");
        m45m.append(i16);
        m45m.append(")");
        return m45m.toString();
    }

    public Bitmap onPostScreenshot(Bitmap bitmap) {
        return bitmap;
    }
}
