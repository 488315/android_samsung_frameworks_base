package com.android.systemui.vibrate;

import android.content.Context;
import android.os.Vibrator;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.LsRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VibrationUtil {
    public final AccessibilityManager mAccessibilityManager;
    public final VibratorType mVibratorType;

    public VibrationUtil(Context context) {
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        if (vibrator == null) {
            this.mVibratorType = VibratorType.create(0);
            return;
        }
        if (LsRune.SECURITY_HAPTIC_FEEDBACK_ON_DC_MOTOR && vibrator.semGetSupportedVibrationType() == 1) {
            this.mVibratorType = VibratorType.create(2);
        } else {
            this.mVibratorType = VibratorType.create(1);
        }
        this.mVibratorType.setVibrator(vibrator);
    }

    public final void playVibration(int i) {
        boolean z = true;
        if (i == 1 && !this.mAccessibilityManager.isTouchExplorationEnabled()) {
            z = false;
        }
        if (z) {
            this.mVibratorType.playVibration(this, i);
        }
    }
}
