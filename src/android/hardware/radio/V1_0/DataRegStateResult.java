package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DataRegStateResult {
    public int regState = 0;
    public int rat = 0;
    public int reasonDataDenied = 0;
    public int maxDataCalls = 0;
    public CellIdentity cellIdentity = new CellIdentity();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != DataRegStateResult.class) {
            return false;
        }
        DataRegStateResult dataRegStateResult = (DataRegStateResult) obj;
        return this.regState == dataRegStateResult.regState && this.rat == dataRegStateResult.rat && this.reasonDataDenied == dataRegStateResult.reasonDataDenied && this.maxDataCalls == dataRegStateResult.maxDataCalls && HidlSupport.deepEquals(this.cellIdentity, dataRegStateResult.cellIdentity);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.regState))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.reasonDataDenied))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxDataCalls))), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentity)));
    }

    public final String toString() {
        return "{.regState = " + RegState.toString(this.regState) + ", .rat = " + this.rat + ", .reasonDataDenied = " + this.reasonDataDenied + ", .maxDataCalls = " + this.maxDataCalls + ", .cellIdentity = " + this.cellIdentity + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(104L), 0L);
    }

    public static final ArrayList<DataRegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataRegStateResult> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 104, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            DataRegStateResult dataRegStateResult = new DataRegStateResult();
            dataRegStateResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 104);
            arrayList.add(dataRegStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.regState = hwBlob.getInt32(j);
        this.rat = hwBlob.getInt32(4 + j);
        this.reasonDataDenied = hwBlob.getInt32(8 + j);
        this.maxDataCalls = hwBlob.getInt32(12 + j);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, j + 16);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(104);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataRegStateResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 104);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 104);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.regState);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(8 + j, this.reasonDataDenied);
        hwBlob.putInt32(12 + j, this.maxDataCalls);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, j + 16);
    }
}
