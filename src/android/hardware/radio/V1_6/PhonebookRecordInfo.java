package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class PhonebookRecordInfo {
    public int recordId = 0;
    public String name = new String();
    public String number = new String();
    public ArrayList<String> emails = new ArrayList<>();
    public ArrayList<String> additionalNumbers = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PhonebookRecordInfo.class) {
            return false;
        }
        PhonebookRecordInfo phonebookRecordInfo = (PhonebookRecordInfo) obj;
        return this.recordId == phonebookRecordInfo.recordId && HidlSupport.deepEquals(this.name, phonebookRecordInfo.name) && HidlSupport.deepEquals(this.number, phonebookRecordInfo.number) && HidlSupport.deepEquals(this.emails, phonebookRecordInfo.emails) && HidlSupport.deepEquals(this.additionalNumbers, phonebookRecordInfo.additionalNumbers);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.recordId))), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(this.number)), Integer.valueOf(HidlSupport.deepHashCode(this.emails)), Integer.valueOf(HidlSupport.deepHashCode(this.additionalNumbers)));
    }

    public final String toString() {
        return "{.recordId = " + this.recordId + ", .name = " + this.name + ", .number = " + this.number + ", .emails = " + this.emails + ", .additionalNumbers = " + this.additionalNumbers + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final ArrayList<PhonebookRecordInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhonebookRecordInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            PhonebookRecordInfo phonebookRecordInfo = new PhonebookRecordInfo();
            phonebookRecordInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(phonebookRecordInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.recordId = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 24;
        this.number = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        int int32 = hwBlob.getInt32(j + 48);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j + 40, true);
        this.emails.clear();
        for (int i = 0; i < int32; i++) {
            new String();
            int i2 = i * 16;
            String string = readEmbeddedBuffer.getString(i2);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), i2, false);
            this.emails.add(string);
        }
        int int322 = hwBlob.getInt32(j + 64);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j + 56, true);
        this.additionalNumbers.clear();
        for (int i3 = 0; i3 < int322; i3++) {
            new String();
            int i4 = i3 * 16;
            String string2 = readEmbeddedBuffer2.getString(i4);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, readEmbeddedBuffer2.handle(), i4, false);
            this.additionalNumbers.add(string2);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhonebookRecordInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.recordId);
        hwBlob.putString(8 + j, this.name);
        hwBlob.putString(24 + j, this.number);
        int size = this.emails.size();
        long j2 = 40 + j;
        hwBlob.putInt32(48 + j, size);
        hwBlob.putBool(52 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.emails.get(i));
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.additionalNumbers.size();
        long j3 = 56 + j;
        hwBlob.putInt32(64 + j, size2);
        hwBlob.putBool(j + 68, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.additionalNumbers.get(i2));
        }
        hwBlob.putBlob(j3, hwBlob3);
    }
}
