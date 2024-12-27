package android.hardware.usb.V1_1;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.usb.V1_0.PortStatus;
import android.os.HidlSupport;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PortStatus_1_1 {
    public int supportedModes;
    public final PortStatus status = new PortStatus();
    public int currentMode = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PortStatus_1_1.class) {
            return false;
        }
        PortStatus_1_1 portStatus_1_1 = (PortStatus_1_1) obj;
        return HidlSupport.deepEquals(this.status, portStatus_1_1.status)
                && HidlSupport.deepEquals(
                        Integer.valueOf(this.supportedModes),
                        Integer.valueOf(portStatus_1_1.supportedModes))
                && this.currentMode == portStatus_1_1.currentMode;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.status)),
                AudioConfig$$ExternalSyntheticOutline0.m(this.supportedModes),
                AudioConfig$$ExternalSyntheticOutline0.m(this.currentMode));
    }

    public final String toString() {
        int i;
        StringBuilder sb = new StringBuilder("{.status = ");
        sb.append(this.status);
        sb.append(", .supportedModes = ");
        int i2 = this.supportedModes;
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m("NONE");
        if ((i2 & 1) == 1) {
            m.add("UFP");
            i = 1;
        } else {
            i = 0;
        }
        if ((i2 & 2) == 2) {
            m.add("DFP");
            i |= 2;
        }
        if ((i2 & 3) == 3) {
            m.add("DRP");
            i = 3;
        }
        int i3 = i2 & 4;
        if (i3 == 4) {
            m.add("NUM_MODES");
            i |= 4;
        }
        if (i3 == 4) {
            m.add("AUDIO_ACCESSORY");
            i |= 4;
        }
        if ((i2 & 8) == 8) {
            m.add("DEBUG_ACCESSORY");
            i |= 8;
        }
        if ((i2 & 16) == 16) {
            m.add("NUM_MODES_1_1");
            i |= 16;
        }
        if (i2 != i) {
            m.add("0x" + Integer.toHexString(i2 & (~i)));
        }
        sb.append(String.join(" | ", m));
        sb.append(", .currentMode = ");
        int i4 = this.currentMode;
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                sb,
                i4 != 0
                        ? i4 == 1
                                ? "UFP"
                                : i4 == 2
                                        ? "DFP"
                                        : i4 == 3
                                                ? "DRP"
                                                : i4 == 4
                                                        ? "NUM_MODES"
                                                        : i4 == 4
                                                                ? "AUDIO_ACCESSORY"
                                                                : i4 == 8
                                                                        ? "DEBUG_ACCESSORY"
                                                                        : i4 == 16
                                                                                ? "NUM_MODES_1_1"
                                                                                : AudioChannelMask$$ExternalSyntheticOutline0
                                                                                        .m(
                                                                                                new StringBuilder(
                                                                                                        "0x"),
                                                                                                i4)
                        : "NONE",
                "}");
    }
}
