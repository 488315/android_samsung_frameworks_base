package com.android.settingslib.display;

import android.util.MathUtils;
import com.samsung.android.knox.custom.CustomDeviceManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BrightnessUtils {
    public static final float convertGammaToLinearFloat(float f, float f2, int i) {
        float norm = MathUtils.norm(0.0f, 65535.0f, i);
        return MathUtils.lerp(f, f2, MathUtils.constrain(norm <= 0.5f ? MathUtils.sq(norm / 0.5f) : MathUtils.exp((norm - 0.5599107f) / 0.17883277f) + 0.28466892f, 0.0f, 12.0f) / 12.0f);
    }

    public static final int convertLinearToGammaFloat(float f, float f2, float f3) {
        float norm = MathUtils.norm(f2, f3, f) * 12.0f;
        return Math.round(MathUtils.lerp(0, CustomDeviceManager.QUICK_PANEL_ALL, norm <= 1.0f ? MathUtils.sqrt(norm) * 0.5f : (MathUtils.log(norm - 0.28466892f) * 0.17883277f) + 0.5599107f));
    }
}
