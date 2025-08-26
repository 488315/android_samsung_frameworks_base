package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SuppSvcNotification {
    public boolean isMT = false;
    public int code = 0;
    public int index = 0;
    public int type = 0;
    public String number = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SuppSvcNotification.class) {
            return false;
        }
        SuppSvcNotification suppSvcNotification = (SuppSvcNotification) obj;
        return this.isMT == suppSvcNotification.isMT && this.code == suppSvcNotification.code && this.index == suppSvcNotification.index && this.type == suppSvcNotification.type && HidlSupport.deepEquals(this.number, suppSvcNotification.number);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isMT))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.code))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.index))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.number)));
    }

    public final String toString() {
        return "{.isMT = " + this.isMT + ", .code = " + this.code + ", .index = " + this.index + ", .type = " + this.type + ", .number = " + this.number + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SuppSvcNotification> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SuppSvcNotification> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SuppSvcNotification suppSvcNotification = new SuppSvcNotification();
            suppSvcNotification.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(suppSvcNotification);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.isMT = hwBlob.getBool(j);
        this.code = hwBlob.getInt32(4 + j);
        this.index = hwBlob.getInt32(8 + j);
        this.type = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SuppSvcNotification> arrayList) {
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
        hwBlob.putBool(j, this.isMT);
        hwBlob.putInt32(4 + j, this.code);
        hwBlob.putInt32(8 + j, this.index);
        hwBlob.putInt32(12 + j, this.type);
        hwBlob.putString(j + 16, this.number);
    }
}
