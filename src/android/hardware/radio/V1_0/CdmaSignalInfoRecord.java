package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaSignalInfoRecord {
    public boolean isPresent = false;
    public byte signalType = 0;
    public byte alertPitch = 0;
    public byte signal = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaSignalInfoRecord.class) {
            return false;
        }
        CdmaSignalInfoRecord cdmaSignalInfoRecord = (CdmaSignalInfoRecord) obj;
        return this.isPresent == cdmaSignalInfoRecord.isPresent && this.signalType == cdmaSignalInfoRecord.signalType && this.alertPitch == cdmaSignalInfoRecord.alertPitch && this.signal == cdmaSignalInfoRecord.signal;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isPresent))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.signalType))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.alertPitch))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.signal))));
    }

    public final String toString() {
        return "{.isPresent = " + this.isPresent + ", .signalType = " + ((int) this.signalType) + ", .alertPitch = " + ((int) this.alertPitch) + ", .signal = " + ((int) this.signal) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public static final ArrayList<CdmaSignalInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSignalInfoRecord> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaSignalInfoRecord cdmaSignalInfoRecord = new CdmaSignalInfoRecord();
            cdmaSignalInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 4);
            arrayList.add(cdmaSignalInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.isPresent = hwBlob.getBool(j);
        this.signalType = hwBlob.getInt8(1 + j);
        this.alertPitch = hwBlob.getInt8(2 + j);
        this.signal = hwBlob.getInt8(j + 3);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(4);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSignalInfoRecord> arrayList) {
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
        hwBlob.putBool(j, this.isPresent);
        hwBlob.putInt8(1 + j, this.signalType);
        hwBlob.putInt8(2 + j, this.alertPitch);
        hwBlob.putInt8(j + 3, this.signal);
    }
}
