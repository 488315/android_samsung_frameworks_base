package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Dial {
    public String address = new String();
    public int clir = 0;
    public ArrayList<UusInfo> uusInfo = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Dial.class) {
            return false;
        }
        Dial dial = (Dial) obj;
        return HidlSupport.deepEquals(this.address, dial.address) && this.clir == dial.clir && HidlSupport.deepEquals(this.uusInfo, dial.uusInfo);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.address)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.clir))), Integer.valueOf(HidlSupport.deepHashCode(this.uusInfo)));
    }

    public final String toString() {
        return "{.address = " + this.address + ", .clir = " + Clir.toString(this.clir) + ", .uusInfo = " + this.uusInfo + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<Dial> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Dial> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            Dial dial = new Dial();
            dial.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(dial);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.address = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        this.clir = hwBlob.getInt32(j + 16);
        int int32 = hwBlob.getInt32(j + 32);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, hwBlob.handle(), j + 24, true);
        this.uusInfo.clear();
        for (int i = 0; i < int32; i++) {
            UusInfo uusInfo = new UusInfo();
            uusInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
            this.uusInfo.add(uusInfo);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Dial> arrayList) {
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
        hwBlob.putString(j, this.address);
        hwBlob.putInt32(16 + j, this.clir);
        int size = this.uusInfo.size();
        long j2 = 24 + j;
        hwBlob.putInt32(32 + j, size);
        hwBlob.putBool(j + 36, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            this.uusInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
