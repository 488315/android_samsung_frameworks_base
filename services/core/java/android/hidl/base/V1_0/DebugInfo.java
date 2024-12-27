package android.hidl.base.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DebugInfo {
    public int pid = 0;
    public long ptr = 0;
    public int arch = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Architecture {
        public static final int IS_32BIT = 2;
        public static final int IS_64BIT = 1;
        public static final int UNKNOWN = 0;

        public static final String dumpBitfield(int i) {
            ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m("UNKNOWN");
            int i2 = 1;
            if ((i & 1) == 1) {
                m.add("IS_64BIT");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                m.add("IS_32BIT");
                i2 |= 2;
            }
            if (i != i2) {
                m.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", m);
        }

        public static final String toString(int i) {
            return i == 0
                    ? "UNKNOWN"
                    : i == 1
                            ? "IS_64BIT"
                            : i == 2
                                    ? "IS_32BIT"
                                    : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("0x"), i);
        }
    }

    public static final ArrayList readVectorFromParcel(HwParcel hwParcel) {
        ArrayList arrayList = new ArrayList();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer =
                hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(debugInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            ((DebugInfo) arrayList.get(i)).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != DebugInfo.class) {
            return false;
        }
        DebugInfo debugInfo = (DebugInfo) obj;
        return this.pid == debugInfo.pid
                && this.ptr == debugInfo.ptr
                && this.arch == debugInfo.arch;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.pid),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.ptr))),
                AudioConfig$$ExternalSyntheticOutline0.m(this.arch));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.pid = hwBlob.getInt32(j);
        this.ptr = hwBlob.getInt64(8 + j);
        this.arch = hwBlob.getInt32(j + 16);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        return "{.pid = "
                + this.pid
                + ", .ptr = "
                + this.ptr
                + ", .arch = "
                + Architecture.toString(this.arch)
                + "}";
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.pid);
        hwBlob.putInt64(8 + j, this.ptr);
        hwBlob.putInt32(j + 16, this.arch);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}
