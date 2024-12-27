package android.hardware.soundtrigger.V2_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

public abstract class SoundModelType {
    public static final String toString(int i) {
        return i == -1
                ? "UNKNOWN"
                : i == 0
                        ? "KEYPHRASE"
                        : i == 1
                                ? "GENERIC"
                                : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("0x"), i);
    }
}
