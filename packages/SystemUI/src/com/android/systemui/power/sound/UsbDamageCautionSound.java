package com.android.systemui.power.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.VibrationEffect;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import java.util.HashSet;

public final class UsbDamageCautionSound extends PowerUiSound {
    public UsbDamageCautionSound(Context context, int i) {
        super(context, i);
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
        if (checkCommonCondition()) {
            playSound(7);
            playVibration(9, PluginEdgeLightingPlus.VERSION, VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION);
        }
    }
}
