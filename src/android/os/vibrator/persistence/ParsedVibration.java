package android.os.vibrator.persistence;

import android.annotation.SystemApi;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class ParsedVibration {
    private final ArrayList<VibrationEffect> mEffects;

    public ParsedVibration(List<VibrationEffect> list) {
        this.mEffects = new ArrayList<>(list);
    }

    public ParsedVibration(VibrationEffect vibrationEffect) {
        ArrayList<VibrationEffect> arrayList = new ArrayList<>(1);
        this.mEffects = arrayList;
        arrayList.add(vibrationEffect);
    }

    @SystemApi
    public VibrationEffect resolve(Vibrator vibrator) {
        return resolve(vibrator.getInfo());
    }

    public VibrationEffect resolve(VibratorInfo vibratorInfo) {
        for (int i = 0; i < this.mEffects.size(); i++) {
            VibrationEffect vibrationEffect = this.mEffects.get(i);
            if (vibratorInfo.areVibrationFeaturesSupported(vibrationEffect)) {
                return vibrationEffect;
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ParsedVibration) {
            return this.mEffects.equals(((ParsedVibration) obj).mEffects);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.mEffects);
    }

    public String toString() {
        return "ParsedVibration{effects=" + this.mEffects + '}';
    }
}
