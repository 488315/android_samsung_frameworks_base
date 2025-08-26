package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaSmsAck {
    public int errorClass = 0;
    public int smsCauseCode = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaSmsAck.class) {
            return false;
        }
        CdmaSmsAck cdmaSmsAck = (CdmaSmsAck) obj;
        return this.errorClass == cdmaSmsAck.errorClass && this.smsCauseCode == cdmaSmsAck.smsCauseCode;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.errorClass))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.smsCauseCode))));
    }

    public final String toString() {
        return "{.errorClass = " + CdmaSmsErrorClass.toString(this.errorClass) + ", .smsCauseCode = " + this.smsCauseCode + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<CdmaSmsAck> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsAck> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaSmsAck cdmaSmsAck = new CdmaSmsAck();
            cdmaSmsAck.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 8);
            arrayList.add(cdmaSmsAck);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.errorClass = hwBlob.getInt32(j);
        this.smsCauseCode = hwBlob.getInt32(j + 4);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsAck> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 8);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.errorClass);
        hwBlob.putInt32(j + 4, this.smsCauseCode);
    }
}
