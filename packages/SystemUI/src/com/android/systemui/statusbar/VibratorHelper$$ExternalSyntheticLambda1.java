package com.android.systemui.statusbar;

import android.media.AudioAttributes;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class VibratorHelper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ VibratorHelper f$0;
    public final /* synthetic */ VibrationEffect f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ VibratorHelper$$ExternalSyntheticLambda1(VibratorHelper vibratorHelper, VibrationEffect vibrationEffect, AudioAttributes audioAttributes) {
        this.f$0 = vibratorHelper;
        this.f$1 = vibrationEffect;
        this.f$2 = audioAttributes;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VibratorHelper vibratorHelper = this.f$0;
                vibratorHelper.mVibrator.vibrate(this.f$1, (AudioAttributes) this.f$2);
                break;
            default:
                VibratorHelper vibratorHelper2 = this.f$0;
                vibratorHelper2.mVibrator.vibrate(this.f$1, (VibrationAttributes) this.f$2);
                break;
        }
    }

    public /* synthetic */ VibratorHelper$$ExternalSyntheticLambda1(VibratorHelper vibratorHelper, VibrationEffect vibrationEffect, VibrationAttributes vibrationAttributes) {
        this.f$0 = vibratorHelper;
        this.f$1 = vibrationEffect;
        this.f$2 = vibrationAttributes;
    }
}
