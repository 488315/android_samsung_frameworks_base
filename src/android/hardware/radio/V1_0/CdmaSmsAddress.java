package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaSmsAddress {
    public int digitMode = 0;
    public int numberMode = 0;
    public int numberType = 0;
    public int numberPlan = 0;
    public ArrayList<Byte> digits = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaSmsAddress.class) {
            return false;
        }
        CdmaSmsAddress cdmaSmsAddress = (CdmaSmsAddress) obj;
        return this.digitMode == cdmaSmsAddress.digitMode && this.numberMode == cdmaSmsAddress.numberMode && this.numberType == cdmaSmsAddress.numberType && this.numberPlan == cdmaSmsAddress.numberPlan && HidlSupport.deepEquals(this.digits, cdmaSmsAddress.digits);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.digitMode))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberMode))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberPlan))), Integer.valueOf(HidlSupport.deepHashCode(this.digits)));
    }

    public final String toString() {
        return "{.digitMode = " + CdmaSmsDigitMode.toString(this.digitMode) + ", .numberMode = " + CdmaSmsNumberMode.toString(this.numberMode) + ", .numberType = " + CdmaSmsNumberType.toString(this.numberType) + ", .numberPlan = " + CdmaSmsNumberPlan.toString(this.numberPlan) + ", .digits = " + this.digits + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<CdmaSmsAddress> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsAddress> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
            cdmaSmsAddress.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(cdmaSmsAddress);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.digitMode = hwBlob.getInt32(j);
        this.numberMode = hwBlob.getInt32(4 + j);
        this.numberType = hwBlob.getInt32(8 + j);
        this.numberPlan = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(j + 24);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j2, true);
        this.digits.clear();
        for (int i = 0; i < int32; i++) {
            this.digits.add(Byte.valueOf(embeddedBuffer.getInt8(i)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsAddress> arrayList) {
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
        hwBlob.putInt32(j, this.digitMode);
        hwBlob.putInt32(4 + j, this.numberMode);
        hwBlob.putInt32(8 + j, this.numberType);
        hwBlob.putInt32(12 + j, this.numberPlan);
        int size = this.digits.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size);
        hwBlob.putBool(j + 28, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.digits.get(i).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
