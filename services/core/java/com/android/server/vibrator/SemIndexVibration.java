package com.android.server.vibrator;

import android.util.Slog;
import com.samsung.android.server.vibrator.CommonPatternInfo;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;
import com.samsung.android.vibrator.VibRune;

/* loaded from: classes3.dex */
public class SemIndexVibration extends SemVibration {
    public SemIndexVibration(SemVibrationBundle semVibrationBundle) {
        super(semVibrationBundle);
    }

    @Override // com.android.server.vibrator.SemVibration
    public HalVibration getVibration() {
        if (!commonValidation()) {
            return null;
        }
        if (isSilentPatternIndex()) {
            Slog.d("VibratorManagerService", "Silent vibration is ignored.");
            return null;
        }
        return new HalVibration(this.mToken, this.mEffect, this.mVibratorHelper.getIndexDuration(this.mIndex), null, this.mMagnitude, -1, null, getCommonData(), getCallerInfo());
    }

    public CommonPatternInfo[] getCommonData() {
        if (VibRune.SUPPORT_HYBRID_HAPTIC() && !SemHapticFeedbackConstants.isRamIndexValid(this.mIndex)) {
            if (SemHapticFeedbackConstants.isResourceIndexValid(this.mIndex)) {
                return this.mVibratorHelper.getResourceIndexData(this.mIndex);
            }
            if (SemHapticFeedbackConstants.isHybridIndexValid(this.mIndex)) {
                return this.mVibratorHelper.getHybridIndexData(this.mIndex);
            }
        }
        return null;
    }

    public final boolean isSilentPatternIndex() {
        return this.mIndex == 50084;
    }

    public String toString() {
        return "semIndexVibrate : " + getCommonLog();
    }
}
