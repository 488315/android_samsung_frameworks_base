package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.VibrationEffect;

/* loaded from: classes3.dex */
public class SemDcVibration extends SemVibration {
    public SemDcVibration(SemVibrationBundle semVibrationBundle) {
        super(semVibrationBundle);
    }

    @Override // com.android.server.vibrator.SemVibration
    public HalVibration getVibration() {
        if (!commonValidation()) {
            return null;
        }
        return new HalVibration(this.mToken, CombinedVibration.createParallel(VibrationEffect.createOneShot(this.mMagnitude, -1)), -1L, null, this.mMagnitude, this.mVibratorHelper.getPatternFrequencyByIndex(this.mIndex), null, null, getCallerInfo());
    }

    public String toString() {
        return "semDcVibrate : " + getCommonLog();
    }
}
