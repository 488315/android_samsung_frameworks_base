package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehPreferredNetworkInfo {
    public int index = 0;
    public String oper = new String();
    public String plmn = new String();
    public int gsmAct = 0;
    public int gsmCompactAct = 0;
    public int utranAct = 0;
    public int mode = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehPreferredNetworkInfo.class) {
            return false;
        }
        SehPreferredNetworkInfo sehPreferredNetworkInfo = (SehPreferredNetworkInfo) obj;
        return this.index == sehPreferredNetworkInfo.index && HidlSupport.deepEquals(this.oper, sehPreferredNetworkInfo.oper) && HidlSupport.deepEquals(this.plmn, sehPreferredNetworkInfo.plmn) && this.gsmAct == sehPreferredNetworkInfo.gsmAct && this.gsmCompactAct == sehPreferredNetworkInfo.gsmCompactAct && this.utranAct == sehPreferredNetworkInfo.utranAct && this.mode == sehPreferredNetworkInfo.mode;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.index))), Integer.valueOf(HidlSupport.deepHashCode(this.oper)), Integer.valueOf(HidlSupport.deepHashCode(this.plmn)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.gsmAct))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.gsmCompactAct))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.utranAct))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mode))));
    }

    public final String toString() {
        return "{.index = " + this.index + ", .oper = " + this.oper + ", .plmn = " + this.plmn + ", .gsmAct = " + this.gsmAct + ", .gsmCompactAct = " + this.gsmCompactAct + ", .utranAct = " + this.utranAct + ", .mode = " + this.mode + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<SehPreferredNetworkInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehPreferredNetworkInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehPreferredNetworkInfo sehPreferredNetworkInfo = new SehPreferredNetworkInfo();
            sehPreferredNetworkInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(sehPreferredNetworkInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.index = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.oper = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 24;
        this.plmn = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        this.gsmAct = hwBlob.getInt32(j + 40);
        this.gsmCompactAct = hwBlob.getInt32(j + 44);
        this.utranAct = hwBlob.getInt32(j + 48);
        this.mode = hwBlob.getInt32(j + 52);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehPreferredNetworkInfo> arrayList) {
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
        hwBlob.putInt32(j, this.index);
        hwBlob.putString(8 + j, this.oper);
        hwBlob.putString(24 + j, this.plmn);
        hwBlob.putInt32(40 + j, this.gsmAct);
        hwBlob.putInt32(44 + j, this.gsmCompactAct);
        hwBlob.putInt32(48 + j, this.utranAct);
        hwBlob.putInt32(j + 52, this.mode);
    }
}
