package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaInformationRecords {
    public ArrayList<CdmaInformationRecord> infoRec = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && obj.getClass() == CdmaInformationRecords.class && HidlSupport.deepEquals(this.infoRec, ((CdmaInformationRecords) obj).infoRec);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.infoRec)));
    }

    public final String toString() {
        return "{.infoRec = " + this.infoRec + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<CdmaInformationRecords> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaInformationRecords> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaInformationRecords cdmaInformationRecords = new CdmaInformationRecords();
            cdmaInformationRecords.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(cdmaInformationRecords);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(8 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, hwBlob.handle(), j, true);
        this.infoRec.clear();
        for (int i = 0; i < int32; i++) {
            CdmaInformationRecord cdmaInformationRecord = new CdmaInformationRecord();
            cdmaInformationRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            this.infoRec.add(cdmaInformationRecord);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaInformationRecords> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        int size = this.infoRec.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            this.infoRec.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(j, hwBlob2);
    }
}
