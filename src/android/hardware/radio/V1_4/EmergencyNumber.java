package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class EmergencyNumber {
    public int categories;
    public int sources;
    public String number = new String();
    public String mcc = new String();
    public String mnc = new String();
    public ArrayList<String> urns = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != EmergencyNumber.class) {
            return false;
        }
        EmergencyNumber emergencyNumber = (EmergencyNumber) obj;
        return HidlSupport.deepEquals(this.number, emergencyNumber.number) && HidlSupport.deepEquals(this.mcc, emergencyNumber.mcc) && HidlSupport.deepEquals(this.mnc, emergencyNumber.mnc) && HidlSupport.deepEquals(Integer.valueOf(this.categories), Integer.valueOf(emergencyNumber.categories)) && HidlSupport.deepEquals(this.urns, emergencyNumber.urns) && HidlSupport.deepEquals(Integer.valueOf(this.sources), Integer.valueOf(emergencyNumber.sources));
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.number)), Integer.valueOf(HidlSupport.deepHashCode(this.mcc)), Integer.valueOf(HidlSupport.deepHashCode(this.mnc)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.categories))), Integer.valueOf(HidlSupport.deepHashCode(this.urns)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sources))));
    }

    public final String toString() {
        return "{.number = " + this.number + ", .mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .categories = " + EmergencyServiceCategory.dumpBitfield(this.categories) + ", .urns = " + this.urns + ", .sources = " + EmergencyNumberSource.dumpBitfield(this.sources) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final ArrayList<EmergencyNumber> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<EmergencyNumber> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            EmergencyNumber emergencyNumber = new EmergencyNumber();
            emergencyNumber.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 80);
            arrayList.add(emergencyNumber);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.number = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.mcc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 32;
        this.mnc = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        this.categories = hwBlob.getInt32(j + 48);
        int int32 = hwBlob.getInt32(j + 64);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j + 56, true);
        this.urns.clear();
        for (int i = 0; i < int32; i++) {
            new String();
            int i2 = i * 16;
            String string = embeddedBuffer.getString(i2);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, embeddedBuffer.handle(), i2, false);
            this.urns.add(string);
        }
        this.sources = hwBlob.getInt32(j + 72);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<EmergencyNumber> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(j, this.number);
        hwBlob.putString(16 + j, this.mcc);
        hwBlob.putString(32 + j, this.mnc);
        hwBlob.putInt32(48 + j, this.categories);
        int size = this.urns.size();
        long j2 = 56 + j;
        hwBlob.putInt32(64 + j, size);
        hwBlob.putBool(68 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.urns.get(i));
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putInt32(j + 72, this.sources);
    }
}
