package com.android.server.vibrator;

import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.VibrationEffect;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
