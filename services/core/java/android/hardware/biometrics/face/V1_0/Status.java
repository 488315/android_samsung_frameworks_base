package android.hardware.biometrics.face.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

public abstract class Status {
    public static final String toString(int i) {
        return i == 0
                ? "OK"
                : i == 1
                        ? "ILLEGAL_ARGUMENT"
                        : i == 2
                                ? "OPERATION_NOT_SUPPORTED"
                                : i == 3
                                        ? "INTERNAL_ERROR"
                                        : i == 4
                                                ? "NOT_ENROLLED"
                                                : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder("0x"), i);
    }
}
