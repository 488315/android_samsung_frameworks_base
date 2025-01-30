package com.android.systemui.power.sound;

import android.content.Context;
import android.os.VibrationEffect;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LowBatterySound extends PowerUiSound {
    public LowBatterySound(Context context, int i) {
        super(context, i);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final void playSoundAndVibration() {
        if (checkCommonCondition()) {
            int i = this.mRingerMode;
            if (2 == i) {
                playSound(3, 1);
                return;
            }
            if (1 == i) {
                playVibration(7, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
            } else if (i == 0) {
                Log.d("PowerUiSound.LowBattery", "RINGER_MODE_SILENT");
            } else {
                Log.e("PowerUiSound.LowBattery", "unknown RINGER_MODE");
            }
        }
    }
}
