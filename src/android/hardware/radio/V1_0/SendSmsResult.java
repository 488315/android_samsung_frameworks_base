package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SendSmsResult {
    public int messageRef = 0;
    public String ackPDU = new String();
    public int errorCode = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SendSmsResult.class) {
            return false;
        }
        SendSmsResult sendSmsResult = (SendSmsResult) obj;
        return this.messageRef == sendSmsResult.messageRef && HidlSupport.deepEquals(this.ackPDU, sendSmsResult.ackPDU) && this.errorCode == sendSmsResult.errorCode;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.messageRef))), Integer.valueOf(HidlSupport.deepHashCode(this.ackPDU)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.errorCode))));
    }

    public final String toString() {
        return "{.messageRef = " + this.messageRef + ", .ackPDU = " + this.ackPDU + ", .errorCode = " + this.errorCode + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SendSmsResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SendSmsResult> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SendSmsResult sendSmsResult = new SendSmsResult();
            sendSmsResult.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(sendSmsResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.messageRef = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.ackPDU = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.errorCode = hwBlob.getInt32(j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SendSmsResult> arrayList) {
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
        hwBlob.putInt32(j + 24, this.errorCode);
    }
}
