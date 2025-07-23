package android.os.vibrator;

import android.os.VibratorInfo;
import android.util.Range;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class VibratorFrequencyProfile {
    private final SparseArray<Float> mFrequenciesOutputAcceleration;
    private final VibratorInfo.FrequencyProfile mFrequencyProfile;

    public VibratorFrequencyProfile(VibratorInfo.FrequencyProfile frequencyProfile) {
        Objects.requireNonNull(frequencyProfile);
        Preconditions.checkArgument(!frequencyProfile.isEmpty(), "Frequency profile must not be empty");
        this.mFrequencyProfile = frequencyProfile;
        this.mFrequenciesOutputAcceleration = generateFrequencyToAccelerationMap(frequencyProfile);
    }

    public SparseArray<Float> getFrequenciesOutputAcceleration() {
        return this.mFrequenciesOutputAcceleration;
    }

    public float getMaxOutputAccelerationGs() {
        return this.mFrequencyProfile.getMaxOutputAccelerationGs();
    }

    public Range<Float> getFrequencyRange(float f) {
        return this.mFrequencyProfile.getFrequencyRangeHz(f);
    }

    public float getOutputAccelerationGs(float f) {
        return this.mFrequencyProfile.getOutputAccelerationGs(f);
    }

    public float getMinFrequencyHz() {
        return this.mFrequencyProfile.getMinFrequencyHz();
    }

    public float getMaxFrequencyHz() {
        return this.mFrequencyProfile.getMaxFrequencyHz();
    }

    private static SparseArray<Float> generateFrequencyToAccelerationMap(VibratorInfo.FrequencyProfile frequencyProfile) {
        float[] frequenciesHz = frequencyProfile.getFrequenciesHz();
        SparseArray<Float> sparseArray = new SparseArray<>(frequenciesHz.length);
        int i = -1;
        for (float f : frequenciesHz) {
            int i2 = (int) f;
            if (i2 != i) {
                sparseArray.put(i2, Float.valueOf(frequencyProfile.getOutputAccelerationGs(i2)));
                i = i2;
            }
        }
        return sparseArray;
    }
}
