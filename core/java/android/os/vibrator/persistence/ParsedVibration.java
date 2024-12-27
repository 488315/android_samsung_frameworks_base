package android.os.vibrator.persistence;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorInfo;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class ParsedVibration {
    private final List<VibrationEffect> mEffects;

    public ParsedVibration(List<VibrationEffect> effects) {
        this.mEffects = effects;
    }

    public ParsedVibration(VibrationEffect effect) {
        this.mEffects = List.of(effect);
    }

    public VibrationEffect resolve(Vibrator vibrator) {
        return resolve(vibrator.getInfo());
    }

    public List<VibrationEffect> getVibrationEffects() {
        return Collections.unmodifiableList(this.mEffects);
    }

    public final VibrationEffect resolve(VibratorInfo info) {
        for (int i = 0; i < this.mEffects.size(); i++) {
            VibrationEffect effect = this.mEffects.get(i);
            if (info.areVibrationFeaturesSupported(effect)) {
                return effect;
            }
        }
        return null;
    }
}
