package android.os.vibrator;

import android.os.VibratorInfo;

/* loaded from: classes3.dex */
public final class VibratorInfoFactory {
    public static VibratorInfo create(int id, VibratorInfo[] vibratorInfos) {
        if (vibratorInfos.length == 0) {
            return new VibratorInfo.Builder(id).build();
        }
        if (vibratorInfos.length == 1) {
            return new VibratorInfo(id, vibratorInfos[0]);
        }
        return new MultiVibratorInfo(id, vibratorInfos);
    }

    private VibratorInfoFactory() {}
}
