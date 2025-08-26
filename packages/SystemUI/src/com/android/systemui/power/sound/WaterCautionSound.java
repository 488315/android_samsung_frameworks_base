package com.android.systemui.power.sound;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class WaterCautionSound extends PowerUiSound {
    public WaterCautionSound(PowerUiSoundBuilder powerUiSoundBuilder) {
        super(powerUiSoundBuilder);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        if (this.mRingerMode == 2) {
            return true;
        }
        this.mRingerMode = 2;
        return true;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final AudioAttributes getAudioAttribute() {
        return new AudioAttributes.Builder().setInternalLegacyStreamType(7).replaceTags(new HashSet()).build();
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final void playSoundAndVibration() {
        playSound(7);
        playVibration(9, PluginEdgeLightingPlus.VERSION, VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION);
    }
}
