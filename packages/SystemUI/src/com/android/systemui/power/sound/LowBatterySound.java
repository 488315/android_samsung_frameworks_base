package com.android.systemui.power.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import com.android.systemui.PowerUiRune;
import java.util.HashSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LowBatterySound extends PowerUiSound {
    public LowBatterySound(Context context, int i) {
        super(context, i);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final AudioAttributes getAudioAttribute() {
        AudioAttributes.Builder replaceTags = new AudioAttributes.Builder().setInternalLegacyStreamType(1).replaceTags(new HashSet());
        if (PowerUiRune.AUDIO_SUPPORT_SITUATION_EXTENSION) {
            replaceTags.semAddAudioTag("stv_low_battery");
        }
        return replaceTags.build();
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final float getVolume() {
        if (PowerUiRune.AUDIO_SUPPORT_SITUATION_EXTENSION) {
            return 1.0f;
        }
        return this.mAudioManager.semGetSituationVolume(11, 0);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final void playSoundAndVibration() {
        if (checkCommonCondition()) {
            int i = this.mRingerMode;
            if (2 == i) {
                playSound(3);
                return;
            }
            if (1 == i) {
                playVibration(7, -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
            } else if (i == 0) {
                Log.d("LowBatterySound", "RINGER_MODE_SILENT");
            } else {
                Log.e("LowBatterySound", "unknown RINGER_MODE");
            }
        }
    }
}
