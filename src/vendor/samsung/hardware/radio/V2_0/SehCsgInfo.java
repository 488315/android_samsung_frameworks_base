package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCsgInfo {
    public int csgId = 0;
    public String name = new String();
    public String plmn = new String();
    public int rat = 0;
    public int category = 0;
    public int signalStrength = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehCsgInfo.class) {
            return false;
        }
        SehCsgInfo sehCsgInfo = (SehCsgInfo) obj;
        return this.csgId == sehCsgInfo.csgId && HidlSupport.deepEquals(this.name, sehCsgInfo.name) && HidlSupport.deepEquals(this.plmn, sehCsgInfo.plmn) && this.rat == sehCsgInfo.rat && this.category == sehCsgInfo.category && this.signalStrength == sehCsgInfo.signalStrength;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csgId))), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(this.plmn)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.category))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalStrength))));
    }

    public final String toString() {
        return "{.csgId = " + this.csgId + ", .name = " + this.name + ", .plmn = " + this.plmn + ", .rat = " + this.rat + ", .category = " + this.category + ", .signalStrength = " + this.signalStrength + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<SehCsgInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehCsgInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehCsgInfo sehCsgInfo = new SehCsgInfo();
            sehCsgInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 56);
            arrayList.add(sehCsgInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.csgId = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 24;
        this.plmn = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        this.rat = hwBlob.getInt32(j + 40);
        this.category = hwBlob.getInt32(j + 44);
        this.signalStrength = hwBlob.getInt32(j + 48);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehCsgInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.csgId);
        hwBlob.putString(8 + j, this.name);
        hwBlob.putString(24 + j, this.plmn);
        hwBlob.putInt32(40 + j, this.rat);
        hwBlob.putInt32(44 + j, this.category);
        hwBlob.putInt32(j + 48, this.signalStrength);
    }
}
