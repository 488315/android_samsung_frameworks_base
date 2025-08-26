package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehPhonebookInfo {
    public int totalCount = 0;
    public int usedCount = 0;
    public int firstIndex = 0;
    public int maxTextLength = 0;
    public int maxNumberLength = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehPhonebookInfo.class) {
            return false;
        }
        SehPhonebookInfo sehPhonebookInfo = (SehPhonebookInfo) obj;
        return this.totalCount == sehPhonebookInfo.totalCount && this.usedCount == sehPhonebookInfo.usedCount && this.firstIndex == sehPhonebookInfo.firstIndex && this.maxTextLength == sehPhonebookInfo.maxTextLength && this.maxNumberLength == sehPhonebookInfo.maxNumberLength;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.totalCount))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.usedCount))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.firstIndex))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxTextLength))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxNumberLength))));
    }

    public final String toString() {
        return "{.totalCount = " + this.totalCount + ", .usedCount = " + this.usedCount + ", .firstIndex = " + this.firstIndex + ", .maxTextLength = " + this.maxTextLength + ", .maxNumberLength = " + this.maxNumberLength + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final ArrayList<SehPhonebookInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehPhonebookInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehPhonebookInfo sehPhonebookInfo = new SehPhonebookInfo();
            sehPhonebookInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 20);
            arrayList.add(sehPhonebookInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.totalCount = hwBlob.getInt32(j);
        this.usedCount = hwBlob.getInt32(4 + j);
        this.firstIndex = hwBlob.getInt32(8 + j);
        this.maxTextLength = hwBlob.getInt32(12 + j);
        this.maxNumberLength = hwBlob.getInt32(j + 16);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehPhonebookInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.totalCount);
        hwBlob.putInt32(4 + j, this.usedCount);
        hwBlob.putInt32(8 + j, this.firstIndex);
        hwBlob.putInt32(12 + j, this.maxTextLength);
        hwBlob.putInt32(j + 16, this.maxNumberLength);
    }
}
