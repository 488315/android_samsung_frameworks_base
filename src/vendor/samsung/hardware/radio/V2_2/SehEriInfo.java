package vendor.samsung.hardware.radio.V2_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehEriInfo {
    public byte roamingIndicator = 0;
    public byte iconIndex = 0;
    public byte iconMode = 0;
    public String eriText = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehEriInfo.class) {
            return false;
        }
        SehEriInfo sehEriInfo = (SehEriInfo) obj;
        return this.roamingIndicator == sehEriInfo.roamingIndicator && this.iconIndex == sehEriInfo.iconIndex && this.iconMode == sehEriInfo.iconMode && HidlSupport.deepEquals(this.eriText, sehEriInfo.eriText);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.roamingIndicator))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.iconIndex))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.iconMode))), Integer.valueOf(HidlSupport.deepHashCode(this.eriText)));
    }

    public final String toString() {
        return "{.roamingIndicator = " + ((int) this.roamingIndicator) + ", .iconIndex = " + ((int) this.iconIndex) + ", .iconMode = " + ((int) this.iconMode) + ", .eriText = " + this.eriText + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<SehEriInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehEriInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehEriInfo sehEriInfo = new SehEriInfo();
            sehEriInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(sehEriInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.roamingIndicator = hwBlob.getInt8(j);
        this.iconIndex = hwBlob.getInt8(1 + j);
        this.iconMode = hwBlob.getInt8(2 + j);
        long j2 = j + 8;
        this.eriText = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehEriInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt8(j, this.roamingIndicator);
        hwBlob.putInt8(1 + j, this.iconIndex);
        hwBlob.putInt8(2 + j, this.iconMode);
        hwBlob.putString(j + 8, this.eriText);
    }
}
