package android.os.vibrator;

import android.os.VibratorInfo;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class VibratorFrequencyProfileLegacy {
    private final VibratorInfo.FrequencyProfileLegacy mFrequencyProfile;

    public VibratorFrequencyProfileLegacy(VibratorInfo.FrequencyProfileLegacy frequencyProfileLegacy) {
        Preconditions.checkArgument(!frequencyProfileLegacy.isEmpty(), "Frequency profile must have a non-empty frequency range");
        this.mFrequencyProfile = frequencyProfileLegacy;
    }

    public float[] getMaxAmplitudeMeasurements() {
        return this.mFrequencyProfile.getMaxAmplitudes();
    }

    public float getMaxAmplitudeMeasurementInterval() {
        return this.mFrequencyProfile.getFrequencyResolutionHz();
    }

    public float getMinFrequency() {
        return this.mFrequencyProfile.getFrequencyRangeHz().getLower().floatValue();
    }

    public float getMaxFrequency() {
        return this.mFrequencyProfile.getFrequencyRangeHz().getUpper().floatValue();
    }
}
