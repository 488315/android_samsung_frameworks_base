package com.samsung.android.globalactions.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

/* loaded from: classes6.dex */
public class ThemeChecker {
    private static final String TAG = "ThemeChecker";
    private final Context mContext;
    private final LogWrapper mLogWrapper;
    private final ScreenCaptureUtil mScreenCaptureUtil;
    private State mState = State.NEED_CHECKING;

    enum State {
        NEED_CHECKING,
        WHITE,
        BLACK
    }

    public ThemeChecker(Context context, ScreenCaptureUtil screenCaptureUtil, LogWrapper logWrapper) {
        this.mContext = context;
        this.mScreenCaptureUtil = screenCaptureUtil;
        this.mLogWrapper = logWrapper;
    }

    public void setThemeState() {
        float[] colorHSV;
        Bitmap bitmapTakeScreenShot = this.mScreenCaptureUtil.takeScreenShot();
        if (bitmapTakeScreenShot == null || (colorHSV = getColorHSV(bitmapTakeScreenShot, new Rect(0, 0, bitmapTakeScreenShot.getWidth(), bitmapTakeScreenShot.getHeight()))) == null) {
            return;
        }
        this.mLogWrapper.v(TAG, "Whole Area Hue=" + colorHSV[0] + ", Saturation=" + colorHSV[1] + ", Brightness=" + colorHSV[2]);
        if (colorHSV[1] < 0.3f && colorHSV[2] >= 0.88f) {
            this.mState = State.WHITE;
        } else {
            this.mState = State.BLACK;
        }
    }

    public boolean isWhiteTheme() {
        if (this.mState == State.NEED_CHECKING) {
            setThemeState();
        }
        return this.mState == State.WHITE;
    }

    public void reset() {
        this.mLogWrapper.v(TAG, "reset() : state reset");
        this.mState = State.NEED_CHECKING;
        this.mScreenCaptureUtil.clearScreenShot();
    }

    public String getState() {
        if (this.mState == State.NEED_CHECKING) {
            setThemeState();
        }
        return this.mState.toString();
    }

    public static float[] getColorHSV(Bitmap bitmap, Rect rect) {
        float[] fArr = new float[3];
        float[] fArr2 = new float[3];
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i = (int) ((width > height ? height : width) / 100.0f);
            if (i <= 0) {
                i = 1;
            }
            int i2 = rect.right;
            int i3 = rect.top;
            int i4 = rect.bottom;
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            int i5 = 0;
            for (int i6 = rect.left; i6 < i2; i6 += i) {
                for (int i7 = i3; i7 < i4; i7 += i) {
                    Color.colorToHSV(bitmap.getPixel(i6, i7), fArr);
                    f += fArr[0];
                    f2 += fArr[1];
                    f3 += fArr[2];
                    i5++;
                }
            }
            float f4 = i5;
            fArr2[0] = f / f4;
            fArr2[1] = f2 / f4;
            fArr2[2] = f3 / f4;
            return fArr2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
