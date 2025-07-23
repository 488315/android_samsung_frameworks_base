package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.SmsWriteArgsStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSimMsgArgs {
    public int index = 0;
    public int status = 0;
    public String pdu = new String();
    public String smsc = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehSimMsgArgs.class) {
            return false;
        }
        SehSimMsgArgs sehSimMsgArgs = (SehSimMsgArgs) obj;
        return this.index == sehSimMsgArgs.index && this.status == sehSimMsgArgs.status && HidlSupport.deepEquals(this.pdu, sehSimMsgArgs.pdu) && HidlSupport.deepEquals(this.smsc, sehSimMsgArgs.smsc);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.index))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(this.pdu)), Integer.valueOf(HidlSupport.deepHashCode(this.smsc)));
    }

    public final String toString() {
        return "{.index = " + this.index + ", .status = " + SmsWriteArgsStatus.toString(this.status) + ", .pdu = " + this.pdu + ", .smsc = " + this.smsc + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<SehSimMsgArgs> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehSimMsgArgs> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehSimMsgArgs sehSimMsgArgs = new SehSimMsgArgs();
            sehSimMsgArgs.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(sehSimMsgArgs);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.index = hwBlob.getInt32(j);
        this.status = hwBlob.getInt32(j + 4);
        long j2 = j + 8;
        this.pdu = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 24;
        this.smsc = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehSimMsgArgs> arrayList) {
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
        hwBlob.putInt32(j, this.index);
        hwBlob.putInt32(4 + j, this.status);
        hwBlob.putString(8 + j, this.pdu);
        hwBlob.putString(j + 24, this.smsc);
    }
}
