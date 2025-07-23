package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.RadioCapabilityPhase;
import android.hardware.radio.V1_0.RadioCapabilityStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RadioCapability {
    public int raf;
    public int session = 0;
    public int phase = 0;
    public String logicalModemUuid = new String();
    public int status = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RadioCapability.class) {
            return false;
        }
        RadioCapability radioCapability = (RadioCapability) obj;
        return this.session == radioCapability.session && this.phase == radioCapability.phase && HidlSupport.deepEquals(Integer.valueOf(this.raf), Integer.valueOf(radioCapability.raf)) && HidlSupport.deepEquals(this.logicalModemUuid, radioCapability.logicalModemUuid) && this.status == radioCapability.status;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.session))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.phase))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.raf))), Integer.valueOf(HidlSupport.deepHashCode(this.logicalModemUuid)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))));
    }

    public final String toString() {
        return "{.session = " + this.session + ", .phase = " + RadioCapabilityPhase.toString(this.phase) + ", .raf = " + RadioAccessFamily.dumpBitfield(this.raf) + ", .logicalModemUuid = " + this.logicalModemUuid + ", .status = " + RadioCapabilityStatus.toString(this.status) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<RadioCapability> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioCapability> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RadioCapability radioCapability = new RadioCapability();
            radioCapability.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(radioCapability);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.session = hwBlob.getInt32(j);
        this.phase = hwBlob.getInt32(4 + j);
        this.raf = hwBlob.getInt32(8 + j);
        long j2 = j + 16;
        this.logicalModemUuid = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.status = hwBlob.getInt32(j + 32);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioCapability> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.session);
        hwBlob.putInt32(4 + j, this.phase);
        hwBlob.putInt32(8 + j, this.raf);
        hwBlob.putString(16 + j, this.logicalModemUuid);
        hwBlob.putInt32(j + 32, this.status);
    }
}
