package com.android.systemui.power.sound;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import java.util.HashSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class BatteryCautionSound extends PowerUiSound {
    public BatteryCautionSound(PowerUiSoundBuilder powerUiSoundBuilder) {
        super(powerUiSoundBuilder);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final AudioAttributes getAudioAttribute() {
        return new AudioAttributes.Builder().setInternalLegacyStreamType(5).replaceTags(new HashSet()).build();
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final void playSoundAndVibration() {
        if (checkCommonCondition()) {
            int i = this.mRingerMode;
            if (i == 2) {
                playSound(4);
                return;
            }
            if (i == 1) {
                playVibration(7, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
            } else if (i == 0) {
                Log.d("BatteryCautionSound", "RINGER_MODE_SILENT");
            } else {
                Log.e("BatteryCautionSound", "unknown RINGER_MODE");
            }
        }
    }
}
