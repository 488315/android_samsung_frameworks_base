package android.hardware.usb.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PortStatus {
    public String portName = new String();
    public int currentDataRole = 0;
    public int currentPowerRole = 0;
    public int currentMode = 0;
    public boolean canChangeMode = false;
    public boolean canChangeDataRole = false;
    public boolean canChangePowerRole = false;
    public int supportedModes = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PortStatus.class) {
            return false;
        }
        PortStatus portStatus = (PortStatus) obj;
        return HidlSupport.deepEquals(this.portName, portStatus.portName)
                && this.currentDataRole == portStatus.currentDataRole
                && this.currentPowerRole == portStatus.currentPowerRole
                && this.currentMode == portStatus.currentMode
                && this.canChangeMode == portStatus.canChangeMode
                && this.canChangeDataRole == portStatus.canChangeDataRole
                && this.canChangePowerRole == portStatus.canChangePowerRole
                && this.supportedModes == portStatus.supportedModes;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.portName)),
                AudioConfig$$ExternalSyntheticOutline0.m(this.currentDataRole),
                AudioConfig$$ExternalSyntheticOutline0.m(this.currentPowerRole),
                AudioConfig$$ExternalSyntheticOutline0.m(this.currentMode),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.canChangeMode),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.canChangeDataRole),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.canChangePowerRole),
                AudioConfig$$ExternalSyntheticOutline0.m(this.supportedModes));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.portName = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j, false);
        this.currentDataRole = hwBlob.getInt32(16 + j);
        this.currentPowerRole = hwBlob.getInt32(20 + j);
        this.currentMode = hwBlob.getInt32(24 + j);
        this.canChangeMode = hwBlob.getBool(28 + j);
        this.canChangeDataRole = hwBlob.getBool(29 + j);
        this.canChangePowerRole = hwBlob.getBool(30 + j);
        this.supportedModes = hwBlob.getInt32(j + 32);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.portName = ");
        sb.append(this.portName);
        sb.append(", .currentDataRole = ");
        int i = this.currentDataRole;
        sb.append(
                i == 0
                        ? "NONE"
                        : i == 1
                                ? "HOST"
                                : i == 2
                                        ? "DEVICE"
                                        : i == 3
                                                ? "NUM_DATA_ROLES"
                                                : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder("0x"), i));
        sb.append(", .currentPowerRole = ");
        int i2 = this.currentPowerRole;
        sb.append(
                i2 != 0
                        ? i2 == 1
                                ? "SOURCE"
                                : i2 == 2
                                        ? "SINK"
                                        : i2 == 3
                                                ? "NUM_POWER_ROLES"
                                                : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder("0x"), i2)
                        : "NONE");
        sb.append(", .currentMode = ");
        sb.append(PortMode.toString(this.currentMode));
        sb.append(", .canChangeMode = ");
        sb.append(this.canChangeMode);
        sb.append(", .canChangeDataRole = ");
        sb.append(this.canChangeDataRole);
        sb.append(", .canChangePowerRole = ");
        sb.append(this.canChangePowerRole);
        sb.append(", .supportedModes = ");
        sb.append(PortMode.toString(this.supportedModes));
        sb.append("}");
        return sb.toString();
    }
}
