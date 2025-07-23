package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_4.RadioTechnology;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehRrcStateInfo {
    public int rat = 0;
    public int state = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehRrcStateInfo.class) {
            return false;
        }
        SehRrcStateInfo sehRrcStateInfo = (SehRrcStateInfo) obj;
        return this.rat == sehRrcStateInfo.rat && this.state == sehRrcStateInfo.state;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.state))));
    }

    public final String toString() {
        return "{.rat = " + RadioTechnology.toString(this.rat) + ", .state = " + this.state + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<SehRrcStateInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehRrcStateInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehRrcStateInfo sehRrcStateInfo = new SehRrcStateInfo();
            sehRrcStateInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
            arrayList.add(sehRrcStateInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.rat = hwBlob.getInt32(j);
        this.state = hwBlob.getInt32(j + 4);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehRrcStateInfo> arrayList) {
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
        hwBlob.putInt32(j, this.rat);
        hwBlob.putInt32(j + 4, this.state);
    }
}
