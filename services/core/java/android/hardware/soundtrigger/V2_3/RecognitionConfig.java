package android.hardware.soundtrigger.V2_3;

import android.hardware.soundtrigger.V2_1.ISoundTriggerHw;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RecognitionConfig {
    public int audioCapabilities;
    public ISoundTriggerHw.SoundModel base;

    public final String toString() {
        return "{.base = "
                + this.base
                + ", .audioCapabilities = "
                + AudioCapabilities.dumpBitfield(this.audioCapabilities)
                + "}";
    }
}
