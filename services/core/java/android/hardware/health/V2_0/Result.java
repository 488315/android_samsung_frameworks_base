package android.hardware.health.V2_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Result {
    public static final String toString(int i) {
        return i == 0
                ? "SUCCESS"
                : i == 1
                        ? "NOT_SUPPORTED"
                        : i == 2
                                ? "UNKNOWN"
                                : i == 3
                                        ? "NOT_FOUND"
                                        : i == 4
                                                ? "CALLBACK_DIED"
                                                : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder("0x"), i);
    }
}
