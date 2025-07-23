package android.os.vibrator;

import android.os.VibratorInfo;

/* loaded from: classes3.dex */
public final class VibratorInfoFactory {
    public static VibratorInfo create(int i, VibratorInfo[] vibratorInfoArr) {
        if (vibratorInfoArr.length == 0) {
            return new VibratorInfo.Builder(i).build();
        }
        if (vibratorInfoArr.length == 1) {
            return new VibratorInfo(i, vibratorInfoArr[0]);
        }
        return new MultiVibratorInfo(i, vibratorInfoArr);
    }

    private VibratorInfoFactory() {
    }
}
