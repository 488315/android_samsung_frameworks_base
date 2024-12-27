package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ConfigFlag {
    public static final String toString(int i) {
        return i == 1
                ? "FORCE_MONO"
                : i == 2
                        ? "FORCE_ANALOG"
                        : i == 3
                                ? "FORCE_DIGITAL"
                                : i == 4
                                        ? "RDS_AF"
                                        : i == 5
                                                ? "RDS_REG"
                                                : i == 6
                                                        ? "DAB_DAB_LINKING"
                                                        : i == 7
                                                                ? "DAB_FM_LINKING"
                                                                : i == 8
                                                                        ? "DAB_DAB_SOFT_LINKING"
                                                                        : i == 9
                                                                                ? "DAB_FM_SOFT_LINKING"
                                                                                : AudioChannelMask$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                new StringBuilder(
                                                                                                        "0x"),
                                                                                                i);
    }
}
