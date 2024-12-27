package android.hardware.usb.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class PortMode {
    public static final String toString(int i) {
        return i == 0
                ? "NONE"
                : i == 1
                        ? "UFP"
                        : i == 2
                                ? "DFP"
                                : i == 3
                                        ? "DRP"
                                        : i == 4
                                                ? "NUM_MODES"
                                                : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder("0x"), i);
    }
}
