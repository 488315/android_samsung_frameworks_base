package com.android.systemui.vibrate;

import android.content.Context;
import android.os.Vibrator;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.LsRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class VibrationUtil {
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
        if (i == 1 && !this.mAccessibilityManager.isTouchExplorationEnabled()) {
            return;
        }
        this.mVibratorType.playVibration(this, i);
    }
}
