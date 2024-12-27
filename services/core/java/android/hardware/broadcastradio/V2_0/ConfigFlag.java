package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;

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
