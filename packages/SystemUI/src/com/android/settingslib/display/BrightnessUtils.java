package com.android.settingslib.display;

import android.util.MathUtils;
import com.samsung.android.knox.custom.CustomDeviceManager;

/* loaded from: classes.dex */
public class BrightnessUtils {
    public static final float convertGammaToLinearFloat(float f, float f2, int i) {
        float fNorm = MathUtils.norm(0.0f, 65535.0f, i);
        return MathUtils.lerp(f, f2, MathUtils.constrain(fNorm <= 0.5f ? MathUtils.sq(fNorm / 0.5f) : MathUtils.exp((fNorm - 0.5599107f) / 0.17883277f) + 0.28466892f, 0.0f, 12.0f) / 12.0f);
    }

    public static final int convertLinearToGammaFloat(float f, float f2, float f3) {
        float fNorm = MathUtils.norm(f2, f3, f) * 12.0f;
        return Math.round(MathUtils.lerp(0, CustomDeviceManager.QUICK_PANEL_ALL, fNorm <= 1.0f ? MathUtils.sqrt(fNorm) * 0.5f : (MathUtils.log(fNorm - 0.28466892f) * 0.17883277f) + 0.5599107f));
    }
}
