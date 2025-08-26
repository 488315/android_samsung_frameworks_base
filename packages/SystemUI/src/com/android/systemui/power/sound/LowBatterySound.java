package com.android.systemui.power.sound;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import com.android.systemui.PowerUiRune;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class LowBatterySound extends PowerUiSound {
    public LowBatterySound(PowerUiSoundBuilder powerUiSoundBuilder) {
        super(powerUiSoundBuilder);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final AudioAttributes getAudioAttribute() {
        AudioAttributes.Builder builderReplaceTags = new AudioAttributes.Builder().setInternalLegacyStreamType(1).replaceTags(new HashSet());
        if (PowerUiRune.AUDIO_SUPPORT_SITUATION_EXTENSION) {
            builderReplaceTags.semAddAudioTag("stv_low_battery");
        }
        return builderReplaceTags.build();
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
