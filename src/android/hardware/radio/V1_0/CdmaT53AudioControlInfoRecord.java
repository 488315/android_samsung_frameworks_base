package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaT53AudioControlInfoRecord {
    public byte upLink = 0;
    public byte downLink = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaT53AudioControlInfoRecord.class) {
            return false;
        }
        CdmaT53AudioControlInfoRecord cdmaT53AudioControlInfoRecord = (CdmaT53AudioControlInfoRecord) obj;
        return this.upLink == cdmaT53AudioControlInfoRecord.upLink && this.downLink == cdmaT53AudioControlInfoRecord.downLink;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.upLink))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.downLink))));
    }

    public final String toString() {
        return "{.upLink = " + ((int) this.upLink) + ", .downLink = " + ((int) this.downLink) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
    }

    public static final ArrayList<CdmaT53AudioControlInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaT53AudioControlInfoRecord> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaT53AudioControlInfoRecord cdmaT53AudioControlInfoRecord = new CdmaT53AudioControlInfoRecord();
            cdmaT53AudioControlInfoRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
            arrayList.add(cdmaT53AudioControlInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.upLink = hwBlob.getInt8(j);
        this.downLink = hwBlob.getInt8(j + 1);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(2);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaT53AudioControlInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 2);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt8(j, this.upLink);
        hwBlob.putInt8(j + 1, this.downLink);
    }
}
