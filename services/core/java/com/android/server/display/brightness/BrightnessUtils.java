package com.android.server.display.brightness;

import android.R;
import android.content.res.Resources;
import android.util.MathUtils;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.DisplayBrightnessState;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BrightnessUtils {
    public static final float sScreenExtendedBrightnessRangeMaximum = Resources.getSystem().getInteger(R.integer.config_vibratorControlServiceDumpSizeLimit) / 255.0f;

    public static float clampAbsoluteBrightness(float f) {
        return MathUtils.constrain(f, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
    }

    public static DisplayBrightnessState constructDisplayBrightnessState(int i, float f, float f2, String str, boolean z) {
        BrightnessReason brightnessReason = new BrightnessReason();
        brightnessReason.setReason(f, i);
        DisplayBrightnessState.Builder builder = new DisplayBrightnessState.Builder();
        builder.mBrightness = f;
        builder.mSdrBrightness = f2;
        builder.mBrightnessReason = brightnessReason;
        builder.mDisplayBrightnessStrategyName = str;
        builder.mIsSlowChange = z;
        return new DisplayBrightnessState(builder);
    }

    public static boolean isValidBrightnessValue(float f) {
        return !Float.isNaN(f) && f >= FullScreenMagnificationGestureHandler.MAX_SCALE && f <= sScreenExtendedBrightnessRangeMaximum;
    }
}
