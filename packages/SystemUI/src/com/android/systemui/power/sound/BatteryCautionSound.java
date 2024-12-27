package com.android.systemui.power.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import java.util.HashSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BatteryCautionSound extends PowerUiSound {
    public BatteryCautionSound(Context context, int i) {
        super(context, i);
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
