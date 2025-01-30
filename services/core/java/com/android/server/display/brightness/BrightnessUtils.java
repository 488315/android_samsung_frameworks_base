package com.android.server.display.brightness;

import android.R;
import android.content.res.Resources;
import android.util.MathUtils;
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.DisplayPowerController2;

/* loaded from: classes2.dex */
public abstract class BrightnessUtils {
    public static float sScreenExtendedBrightnessRangeMaximum = Resources.getSystem().getInteger(R.integer.config_screenTimeoutOverride) / 255.0f;

    public static boolean isValidBrightnessValue(float f) {
        return !Float.isNaN(f) && f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f <= sScreenExtendedBrightnessRangeMaximum;
    }

    public static float clampAbsoluteBrightness(float f) {
        return MathUtils.constrain(f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
    }

    public static float clampBrightnessAdjustment(float f) {
        return MathUtils.constrain(f, -1.0f, 1.0f);
    }

    public static DisplayBrightnessState constructDisplayBrightnessState(int i, float f, float f2, String str) {
        BrightnessReason brightnessReason = new BrightnessReason();
        brightnessReason.setReason(i, f);
        return new DisplayBrightnessState.Builder().setBrightness(f).setSdrBrightness(f2).setBrightnessReason(brightnessReason).setDisplayBrightnessStrategyName(str).build();
    }
}
