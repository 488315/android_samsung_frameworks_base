package com.android.server.vibrator;

import android.content.Context;
import android.os.vibrator.SemHapticSegment;
import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.vibrator.VibRune;

/* loaded from: classes3.dex */
public class SemVibrationFactory {
    public SemVibration createSemVibration(Context context, SemVibrationBundle semVibrationBundle, SemHapticSegment semHapticSegment, VibrationSettings vibrationSettings) {
        if (VibRune.SUPPORT_CUSTOM_PATTERN && semHapticSegment.isCustomIndexValid()) {
            return new SemCustomVibration(context, semVibrationBundle, semHapticSegment, vibrationSettings);
        }
        if (VibratorHelper.isDcMotorClickIndex(semVibrationBundle.getIndex())) {
            semVibrationBundle.setMagnitude(vibrationSettings.getCurrentMagnitude(18));
            return new SemDcVibration(semVibrationBundle);
        }
        if (VibRune.SUPPORT_RAM_INDEX_HAPTIC() || VibRune.SUPPORT_HYBRID_HAPTIC()) {
            return new SemIndexVibration(semVibrationBundle);
        }
        return new SemPatternVibration(semVibrationBundle);
    }
}
