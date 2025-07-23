package com.android.systemui.screenshot;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FloatingWindowUtil {
    public static float dpToPx(DisplayMetrics displayMetrics, float f) {
        return (f * displayMetrics.densityDpi) / 160.0f;
    }

    public static WindowManager.LayoutParams getFloatingWindowParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 918816, -3);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        return layoutParams;
    }
}
