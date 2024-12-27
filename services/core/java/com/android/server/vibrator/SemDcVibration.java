package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationEffect;

public final class SemDcVibration extends SemVibration {
    @Override // com.android.server.vibrator.SemVibration
    public final HalVibration getVibration() {
        if (!commonValidation()) {
            return null;
        }
        CombinedVibration createParallel =
                CombinedVibration.createParallel(
                        VibrationEffect.createOneShot(this.mMagnitude, -1));
        IBinder iBinder = this.mToken;
        this.mVibratorHelper.getPatternFrequencyByIndex(this.mIndex);
        return new HalVibration(
                iBinder, createParallel, -1L, this.mMagnitude, null, null, getCallerInfo());
    }

    public final String toString() {
        return "semDcVibrate : " + getCommonLog();
    }
}
