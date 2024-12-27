package android.hardware.biometrics.face.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
