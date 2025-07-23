package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaSmsSubaddress {
    public int subaddressType = 0;
    public boolean odd = false;
    public ArrayList<Byte> digits = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaSmsSubaddress.class) {
            return false;
        }
        CdmaSmsSubaddress cdmaSmsSubaddress = (CdmaSmsSubaddress) obj;
        return this.subaddressType == cdmaSmsSubaddress.subaddressType && this.odd == cdmaSmsSubaddress.odd && HidlSupport.deepEquals(this.digits, cdmaSmsSubaddress.digits);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.subaddressType))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.odd))), Integer.valueOf(HidlSupport.deepHashCode(this.digits)));
    }

    public final String toString() {
        return "{.subaddressType = " + CdmaSmsSubaddressType.toString(this.subaddressType) + ", .odd = " + this.odd + ", .digits = " + this.digits + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<CdmaSmsSubaddress> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsSubaddress> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaSmsSubaddress cdmaSmsSubaddress = new CdmaSmsSubaddress();
            cdmaSmsSubaddress.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(cdmaSmsSubaddress);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.subaddressType = hwBlob.getInt32(j);
        this.odd = hwBlob.getBool(4 + j);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j + 16);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j2, true);
        this.digits.clear();
        for (int i = 0; i < int32; i++) {
            this.digits.add(Byte.valueOf(readEmbeddedBuffer.getInt8(i)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsSubaddress> arrayList) {
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
        hwBlob.putInt32(j, this.subaddressType);
        hwBlob.putBool(4 + j, this.odd);
        int size = this.digits.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(j + 20, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.digits.get(i).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
