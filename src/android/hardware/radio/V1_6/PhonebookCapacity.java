package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class PhonebookCapacity {
    public int maxAdnRecords = 0;
    public int usedAdnRecords = 0;
    public int maxEmailRecords = 0;
    public int usedEmailRecords = 0;
    public int maxAdditionalNumberRecords = 0;
    public int usedAdditionalNumberRecords = 0;
    public int maxNameLen = 0;
    public int maxNumberLen = 0;
    public int maxEmailLen = 0;
    public int maxAdditionalNumberLen = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PhonebookCapacity.class) {
            return false;
        }
        PhonebookCapacity phonebookCapacity = (PhonebookCapacity) obj;
        return this.maxAdnRecords == phonebookCapacity.maxAdnRecords && this.usedAdnRecords == phonebookCapacity.usedAdnRecords && this.maxEmailRecords == phonebookCapacity.maxEmailRecords && this.usedEmailRecords == phonebookCapacity.usedEmailRecords && this.maxAdditionalNumberRecords == phonebookCapacity.maxAdditionalNumberRecords && this.usedAdditionalNumberRecords == phonebookCapacity.usedAdditionalNumberRecords && this.maxNameLen == phonebookCapacity.maxNameLen && this.maxNumberLen == phonebookCapacity.maxNumberLen && this.maxEmailLen == phonebookCapacity.maxEmailLen && this.maxAdditionalNumberLen == phonebookCapacity.maxAdditionalNumberLen;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxAdnRecords))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.usedAdnRecords))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxEmailRecords))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.usedEmailRecords))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxAdditionalNumberRecords))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.usedAdditionalNumberRecords))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxNameLen))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxNumberLen))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxEmailLen))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxAdditionalNumberLen))));
    }

    public final String toString() {
        return "{.maxAdnRecords = " + this.maxAdnRecords + ", .usedAdnRecords = " + this.usedAdnRecords + ", .maxEmailRecords = " + this.maxEmailRecords + ", .usedEmailRecords = " + this.usedEmailRecords + ", .maxAdditionalNumberRecords = " + this.maxAdditionalNumberRecords + ", .usedAdditionalNumberRecords = " + this.usedAdditionalNumberRecords + ", .maxNameLen = " + this.maxNameLen + ", .maxNumberLen = " + this.maxNumberLen + ", .maxEmailLen = " + this.maxEmailLen + ", .maxAdditionalNumberLen = " + this.maxAdditionalNumberLen + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<PhonebookCapacity> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhonebookCapacity> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            PhonebookCapacity phonebookCapacity = new PhonebookCapacity();
            phonebookCapacity.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(phonebookCapacity);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.maxAdnRecords = hwBlob.getInt32(j);
        this.usedAdnRecords = hwBlob.getInt32(4 + j);
        this.maxEmailRecords = hwBlob.getInt32(8 + j);
        this.usedEmailRecords = hwBlob.getInt32(12 + j);
        this.maxAdditionalNumberRecords = hwBlob.getInt32(16 + j);
        this.usedAdditionalNumberRecords = hwBlob.getInt32(20 + j);
        this.maxNameLen = hwBlob.getInt32(24 + j);
        this.maxNumberLen = hwBlob.getInt32(28 + j);
        this.maxEmailLen = hwBlob.getInt32(32 + j);
        this.maxAdditionalNumberLen = hwBlob.getInt32(j + 36);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhonebookCapacity> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.maxAdnRecords);
        hwBlob.putInt32(4 + j, this.usedAdnRecords);
        hwBlob.putInt32(8 + j, this.maxEmailRecords);
        hwBlob.putInt32(12 + j, this.usedEmailRecords);
        hwBlob.putInt32(16 + j, this.maxAdditionalNumberRecords);
        hwBlob.putInt32(20 + j, this.usedAdditionalNumberRecords);
        hwBlob.putInt32(24 + j, this.maxNameLen);
        hwBlob.putInt32(28 + j, this.maxNumberLen);
        hwBlob.putInt32(32 + j, this.maxEmailLen);
        hwBlob.putInt32(j + 36, this.maxAdditionalNumberLen);
    }
}
