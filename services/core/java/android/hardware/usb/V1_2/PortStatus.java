package android.hardware.usb.V1_2;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.usb.V1_1.PortStatus_1_1;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.os.HidlSupport;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PortStatus {
    public int contaminantDetectionStatus;
    public int contaminantProtectionStatus;
    public PortStatus_1_1 status_1_1;
    public int supportedContaminantProtectionModes;
    public boolean supportsEnableContaminantPresenceDetection;
    public boolean supportsEnableContaminantPresenceProtection;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PortStatus.class) {
            return false;
        }
        PortStatus portStatus = (PortStatus) obj;
        return HidlSupport.deepEquals(this.status_1_1, portStatus.status_1_1)
                && HidlSupport.deepEquals(
                        Integer.valueOf(this.supportedContaminantProtectionModes),
                        Integer.valueOf(portStatus.supportedContaminantProtectionModes))
                && this.supportsEnableContaminantPresenceProtection
                        == portStatus.supportsEnableContaminantPresenceProtection
                && this.contaminantProtectionStatus == portStatus.contaminantProtectionStatus
                && this.supportsEnableContaminantPresenceDetection
                        == portStatus.supportsEnableContaminantPresenceDetection
                && this.contaminantDetectionStatus == portStatus.contaminantDetectionStatus;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.status_1_1)),
                AudioConfig$$ExternalSyntheticOutline0.m(this.supportedContaminantProtectionModes),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        this.supportsEnableContaminantPresenceProtection),
                AudioConfig$$ExternalSyntheticOutline0.m(this.contaminantProtectionStatus),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        this.supportsEnableContaminantPresenceDetection),
                AudioConfig$$ExternalSyntheticOutline0.m(this.contaminantDetectionStatus));
    }

    public final String toString() {
        int i;
        StringBuilder sb = new StringBuilder("{.status_1_1 = ");
        sb.append(this.status_1_1);
        sb.append(", .supportedContaminantProtectionModes = ");
        int i2 = this.supportedContaminantProtectionModes;
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m("NONE");
        if ((i2 & 1) == 1) {
            m.add("FORCE_SINK");
            i = 1;
        } else {
            i = 0;
        }
        if ((i2 & 2) == 2) {
            m.add("FORCE_SOURCE");
            i |= 2;
        }
        if ((i2 & 4) == 4) {
            m.add("FORCE_DISABLE");
            i |= 4;
        }
        if (i2 != i) {
            m.add("0x" + Integer.toHexString(i2 & (~i)));
        }
        sb.append(String.join(" | ", m));
        sb.append(", .supportsEnableContaminantPresenceProtection = ");
        sb.append(this.supportsEnableContaminantPresenceProtection);
        sb.append(", .contaminantProtectionStatus = ");
        int i3 = this.contaminantProtectionStatus;
        String str = "DISABLED";
        sb.append(
                i3 != 0
                        ? i3 == 1
                                ? "FORCE_SINK"
                                : i3 == 2
                                        ? "FORCE_SOURCE"
                                        : i3 == 4
                                                ? "FORCE_DISABLE"
                                                : i3 == 8
                                                        ? "DISABLED"
                                                        : AudioChannelMask$$ExternalSyntheticOutline0
                                                                .m(new StringBuilder("0x"), i3)
                        : "NONE");
        sb.append(", .supportsEnableContaminantPresenceDetection = ");
        sb.append(this.supportsEnableContaminantPresenceDetection);
        sb.append(", .contaminantDetectionStatus = ");
        int i4 = this.contaminantDetectionStatus;
        if (i4 == 0) {
            str = "NOT_SUPPORTED";
        } else if (i4 != 1) {
            str =
                    i4 == 2
                            ? "NOT_DETECTED"
                            : i4 == 3
                                    ? "DETECTED"
                                    : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("0x"), i4);
        }
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str, "}");
    }
}
