package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehImsCall {
    public int state = 0;
    public int type = 0;
    public int isMt = 0;
    public int isMpty = 0;
    public String number = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehImsCall.class) {
            return false;
        }
        SehImsCall sehImsCall = (SehImsCall) obj;
        return this.state == sehImsCall.state && this.type == sehImsCall.type && this.isMt == sehImsCall.isMt && this.isMpty == sehImsCall.isMpty && HidlSupport.deepEquals(this.number, sehImsCall.number);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.state))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.isMt))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.isMpty))), Integer.valueOf(HidlSupport.deepHashCode(this.number)));
    }

    public final String toString() {
        return "{.state = " + this.state + ", .type = " + this.type + ", .isMt = " + this.isMt + ", .isMpty = " + this.isMpty + ", .number = " + this.number + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SehImsCall> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehImsCall> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehImsCall sehImsCall = new SehImsCall();
            sehImsCall.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(sehImsCall);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.state = hwBlob.getInt32(j);
        this.type = hwBlob.getInt32(4 + j);
        this.isMt = hwBlob.getInt32(8 + j);
        this.isMpty = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehImsCall> arrayList) {
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
        hwBlob.putInt32(j, this.state);
        hwBlob.putInt32(4 + j, this.type);
        hwBlob.putInt32(8 + j, this.isMt);
        hwBlob.putInt32(12 + j, this.isMpty);
        hwBlob.putString(j + 16, this.number);
    }
}
