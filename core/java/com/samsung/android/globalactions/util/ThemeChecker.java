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
        Bitmap currentBG = this.mScreenCaptureUtil.takeScreenShot();
        if (currentBG != null) {
            Rect targetRegion = new Rect(0, 0, currentBG.getWidth(), currentBG.getHeight());
            float[] colorHSV = getColorHSV(currentBG, targetRegion);
            if (colorHSV != null) {
                this.mLogWrapper.v(TAG, "Whole Area Hue=" + colorHSV[0] + ", Saturation=" + colorHSV[1] + ", Brightness=" + colorHSV[2]);
                if (colorHSV[1] < 0.3f && colorHSV[2] >= 0.88f) {
                    this.mState = State.WHITE;
                } else {
                    this.mState = State.BLACK;
                }
            }
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

    public static float[] getColorHSV(Bitmap bitmap, Rect region) {
        float sumHue = 0.0f;
        float sumSaturation = 0.0f;
        float sumValue = 0.0f;
        float[] pixelHSV = new float[3];
        float[] result = new float[3];
        int sampleCount = 0;
        try {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int step = (int) ((w > h ? h : w) / 100.0f);
            if (step <= 0) {
                step = 1;
            }
            int left = region.left;
            int right = region.right;
            int top = region.top;
            int bottom = region.bottom;
            for (int x = left; x < right; x += step) {
                int y = top;
                while (y < bottom) {
                    int w2 = w;
                    try {
                        Color.colorToHSV(bitmap.getPixel(x, y), pixelHSV);
                        sumHue += pixelHSV[0];
                        sumSaturation += pixelHSV[1];
                        sumValue += pixelHSV[2];
                        sampleCount++;
                        y += step;
                        w = w2;
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            result[0] = sumHue / sampleCount;
            result[1] = sumSaturation / sampleCount;
            result[2] = sumValue / sampleCount;
            return result;
        } catch (Exception e2) {
            e = e2;
        }
    }
}
