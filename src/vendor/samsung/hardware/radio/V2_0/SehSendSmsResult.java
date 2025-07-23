package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSendSmsResult {
    public int messageRef = 0;
    public String ackPDU = new String();
    public int errorCode = 0;
    public int errorClass = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehSendSmsResult.class) {
            return false;
        }
        SehSendSmsResult sehSendSmsResult = (SehSendSmsResult) obj;
        return this.messageRef == sehSendSmsResult.messageRef && HidlSupport.deepEquals(this.ackPDU, sehSendSmsResult.ackPDU) && this.errorCode == sehSendSmsResult.errorCode && this.errorClass == sehSendSmsResult.errorClass;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.messageRef))), Integer.valueOf(HidlSupport.deepHashCode(this.ackPDU)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.errorCode))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.errorClass))));
    }

    public final String toString() {
        return "{.messageRef = " + this.messageRef + ", .ackPDU = " + this.ackPDU + ", .errorCode = " + this.errorCode + ", .errorClass = " + this.errorClass + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SehSendSmsResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehSendSmsResult> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehSendSmsResult sehSendSmsResult = new SehSendSmsResult();
            sehSendSmsResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(sehSendSmsResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.messageRef = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.ackPDU = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.errorCode = hwBlob.getInt32(24 + j);
        this.errorClass = hwBlob.getInt32(j + 28);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehSendSmsResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.messageRef);
        hwBlob.putString(8 + j, this.ackPDU);
        hwBlob.putInt32(24 + j, this.errorCode);
        hwBlob.putInt32(j + 28, this.errorClass);
    }
}
