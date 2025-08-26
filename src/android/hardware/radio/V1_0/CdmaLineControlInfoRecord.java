package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaLineControlInfoRecord {
    public byte lineCtrlPolarityIncluded = 0;
    public byte lineCtrlToggle = 0;
    public byte lineCtrlReverse = 0;
    public byte lineCtrlPowerDenial = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaLineControlInfoRecord.class) {
            return false;
        }
        CdmaLineControlInfoRecord cdmaLineControlInfoRecord = (CdmaLineControlInfoRecord) obj;
        return this.lineCtrlPolarityIncluded == cdmaLineControlInfoRecord.lineCtrlPolarityIncluded && this.lineCtrlToggle == cdmaLineControlInfoRecord.lineCtrlToggle && this.lineCtrlReverse == cdmaLineControlInfoRecord.lineCtrlReverse && this.lineCtrlPowerDenial == cdmaLineControlInfoRecord.lineCtrlPowerDenial;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.lineCtrlPolarityIncluded))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.lineCtrlToggle))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.lineCtrlReverse))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.lineCtrlPowerDenial))));
    }

    public final String toString() {
        return "{.lineCtrlPolarityIncluded = " + ((int) this.lineCtrlPolarityIncluded) + ", .lineCtrlToggle = " + ((int) this.lineCtrlToggle) + ", .lineCtrlReverse = " + ((int) this.lineCtrlReverse) + ", .lineCtrlPowerDenial = " + ((int) this.lineCtrlPowerDenial) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public static final ArrayList<CdmaLineControlInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaLineControlInfoRecord> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaLineControlInfoRecord cdmaLineControlInfoRecord = new CdmaLineControlInfoRecord();
            cdmaLineControlInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 4);
            arrayList.add(cdmaLineControlInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.lineCtrlPolarityIncluded = hwBlob.getInt8(j);
        this.lineCtrlToggle = hwBlob.getInt8(1 + j);
        this.lineCtrlReverse = hwBlob.getInt8(2 + j);
        this.lineCtrlPowerDenial = hwBlob.getInt8(j + 3);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(4);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaLineControlInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 4);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt8(j, this.lineCtrlPolarityIncluded);
        hwBlob.putInt8(1 + j, this.lineCtrlToggle);
        hwBlob.putInt8(2 + j, this.lineCtrlReverse);
        hwBlob.putInt8(j + 3, this.lineCtrlPowerDenial);
    }
}
