package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GsmSmsMessage {
    public String smscPdu = new String();
    public String pdu = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != GsmSmsMessage.class) {
            return false;
        }
        GsmSmsMessage gsmSmsMessage = (GsmSmsMessage) obj;
        return HidlSupport.deepEquals(this.smscPdu, gsmSmsMessage.smscPdu) && HidlSupport.deepEquals(this.pdu, gsmSmsMessage.pdu);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.smscPdu)), Integer.valueOf(HidlSupport.deepHashCode(this.pdu)));
    }

    public final String toString() {
        return "{.smscPdu = " + this.smscPdu + ", .pdu = " + this.pdu + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<GsmSmsMessage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GsmSmsMessage> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            GsmSmsMessage gsmSmsMessage = new GsmSmsMessage();
            gsmSmsMessage.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(gsmSmsMessage);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.smscPdu = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r1.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.pdu = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GsmSmsMessage> arrayList) {
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
        hwBlob.putString(j, this.smscPdu);
        hwBlob.putString(j + 16, this.pdu);
    }
}
