package com.android.systemui.power.sound;

import android.content.Context;
import android.os.VibrationEffect;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WaterCautionSound extends PowerUiSound {
    public WaterCautionSound(Context context, int i) {
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
    public final void playSoundAndVibration() {
        playSound(7, 7);
        playVibration(9, PluginEdgeLightingPlus.VERSION, VibrationEffect.SemMagnitudeType.TYPE_NOTIFICATION);
    }
}
