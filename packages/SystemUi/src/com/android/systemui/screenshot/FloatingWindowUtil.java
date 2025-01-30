package com.android.systemui.screenshot;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FloatingWindowUtil {
    public static float dpToPx(DisplayMetrics displayMetrics, float f) {
        return (f * displayMetrics.densityDpi) / 160.0f;
    }

    public static WindowManager.LayoutParams getFloatingWindowParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 918816, -3);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT;
        return layoutParams;
    }
}
