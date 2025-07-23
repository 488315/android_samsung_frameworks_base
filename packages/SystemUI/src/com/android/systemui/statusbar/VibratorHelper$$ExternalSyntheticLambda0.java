package com.android.systemui.statusbar;

import android.media.AudioAttributes;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VibratorHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ VibratorHelper f$0;
    public final /* synthetic */ VibrationEffect f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ VibratorHelper$$ExternalSyntheticLambda0(VibratorHelper vibratorHelper, VibrationEffect vibrationEffect, AudioAttributes audioAttributes) {
        this.f$0 = vibratorHelper;
        this.f$1 = vibrationEffect;
        this.f$2 = audioAttributes;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VibratorHelper vibratorHelper = this.f$0;
                vibratorHelper.mVibrator.vibrate(this.f$1, (VibrationAttributes) this.f$2);
                break;
            default:
                VibratorHelper vibratorHelper2 = this.f$0;
                vibratorHelper2.mVibrator.vibrate(this.f$1, (AudioAttributes) this.f$2);
                break;
        }
    }

    public /* synthetic */ VibratorHelper$$ExternalSyntheticLambda0(VibratorHelper vibratorHelper, VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes) {
        this.f$0 = vibratorHelper;
        this.f$1 = vibrationEffect;
        this.f$2 = vibrationAttributes;
    }
}
