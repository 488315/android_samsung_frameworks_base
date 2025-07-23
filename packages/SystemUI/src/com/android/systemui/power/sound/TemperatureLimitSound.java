package com.android.systemui.power.sound;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.HashSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TemperatureLimitSound extends PowerUiSound {
    public TemperatureLimitSound(PowerUiSoundBuilder powerUiSoundBuilder) {
        super(powerUiSoundBuilder);
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final boolean checkCondition() {
        if (this.mRingerMode == 0) {
            this.mRingerMode = 1;
        }
        return true;
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final AudioAttributes getAudioAttribute() {
        return new AudioAttributes.Builder().setInternalLegacyStreamType(7).replaceTags(new HashSet()).build();
    }

    @Override // com.android.systemui.power.sound.PowerUiSound
    public final void playSoundAndVibration() {
        if (checkCommonCondition()) {
            playSound(7);
            playVibration(9, PluginEdgeLightingPlus.VERSION, VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION);
        }
    }
}
