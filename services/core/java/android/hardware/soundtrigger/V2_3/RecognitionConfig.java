package android.hardware.soundtrigger.V2_3;

import android.hardware.soundtrigger.V2_1.ISoundTriggerHw;

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
