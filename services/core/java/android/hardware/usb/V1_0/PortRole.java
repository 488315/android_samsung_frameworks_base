package android.hardware.usb.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.Objects;

public final class PortRole {
    public int type = 0;
    public int role = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PortRole.class) {
            return false;
        }
        PortRole portRole = (PortRole) obj;
        return this.type == portRole.type && this.role == portRole.role;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.type),
                AudioConfig$$ExternalSyntheticOutline0.m(this.role));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.type = ");
        int i = this.type;
        sb.append(
                i == 0
                        ? "DATA_ROLE"
                        : i == 1
                                ? "POWER_ROLE"
                                : i == 2
                                        ? "MODE"
                                        : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("0x"), i));
        sb.append(", .role = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.role, sb, "}");
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        hwBlob.putInt32(0L, this.type);
        hwBlob.putInt32(4L, this.role);
        hwParcel.writeBuffer(hwBlob);
    }
}
