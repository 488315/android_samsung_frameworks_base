package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSignalBar {
    public int cdmaLevel = 0;
    public int evdoLevel = 0;
    public int gsmLevel = 0;
    public int wcdmaLevel = 0;
    public int tdscdmaLevel = 0;
    public int lteLevel = 0;
    public int nrLevel = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehSignalBar.class) {
            return false;
        }
        SehSignalBar sehSignalBar = (SehSignalBar) obj;
        return this.cdmaLevel == sehSignalBar.cdmaLevel && this.evdoLevel == sehSignalBar.evdoLevel && this.gsmLevel == sehSignalBar.gsmLevel && this.wcdmaLevel == sehSignalBar.wcdmaLevel && this.tdscdmaLevel == sehSignalBar.tdscdmaLevel && this.lteLevel == sehSignalBar.lteLevel && this.nrLevel == sehSignalBar.nrLevel;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cdmaLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.evdoLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.gsmLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.wcdmaLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.tdscdmaLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lteLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nrLevel))));
    }

    public final String toString() {
        return "{.cdmaLevel = " + SehSignalLevel.toString(this.cdmaLevel) + ", .evdoLevel = " + SehSignalLevel.toString(this.evdoLevel) + ", .gsmLevel = " + SehSignalLevel.toString(this.gsmLevel) + ", .wcdmaLevel = " + SehSignalLevel.toString(this.wcdmaLevel) + ", .tdscdmaLevel = " + SehSignalLevel.toString(this.tdscdmaLevel) + ", .lteLevel = " + SehSignalLevel.toString(this.lteLevel) + ", .nrLevel = " + SehSignalLevel.toString(this.nrLevel) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(28L), 0L);
    }

    public static final ArrayList<SehSignalBar> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehSignalBar> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 28, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehSignalBar sehSignalBar = new SehSignalBar();
            sehSignalBar.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 28);
            arrayList.add(sehSignalBar);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cdmaLevel = hwBlob.getInt32(j);
        this.evdoLevel = hwBlob.getInt32(4 + j);
        this.gsmLevel = hwBlob.getInt32(8 + j);
        this.wcdmaLevel = hwBlob.getInt32(12 + j);
        this.tdscdmaLevel = hwBlob.getInt32(16 + j);
        this.lteLevel = hwBlob.getInt32(20 + j);
        this.nrLevel = hwBlob.getInt32(j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(28);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehSignalBar> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 28);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 28);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.cdmaLevel);
        hwBlob.putInt32(4 + j, this.evdoLevel);
        hwBlob.putInt32(8 + j, this.gsmLevel);
        hwBlob.putInt32(12 + j, this.wcdmaLevel);
        hwBlob.putInt32(16 + j, this.tdscdmaLevel);
        hwBlob.putInt32(20 + j, this.lteLevel);
        hwBlob.putInt32(j + 24, this.nrLevel);
    }
}
