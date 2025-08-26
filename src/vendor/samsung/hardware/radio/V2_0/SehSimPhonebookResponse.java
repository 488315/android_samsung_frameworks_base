package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSimPhonebookResponse {
    public ArrayList<Integer> lengthAlphas = new ArrayList<>();
    public ArrayList<Integer> dataTypeAlphas = new ArrayList<>();
    public ArrayList<String> alphaTags = new ArrayList<>();
    public ArrayList<Integer> lengthNumbers = new ArrayList<>();
    public ArrayList<Integer> dataTypeNumbers = new ArrayList<>();
    public ArrayList<String> numbers = new ArrayList<>();
    public int recordIndex = 0;
    public int nextIndex = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehSimPhonebookResponse.class) {
            return false;
        }
        SehSimPhonebookResponse sehSimPhonebookResponse = (SehSimPhonebookResponse) obj;
        return HidlSupport.deepEquals(this.lengthAlphas, sehSimPhonebookResponse.lengthAlphas) && HidlSupport.deepEquals(this.dataTypeAlphas, sehSimPhonebookResponse.dataTypeAlphas) && HidlSupport.deepEquals(this.alphaTags, sehSimPhonebookResponse.alphaTags) && HidlSupport.deepEquals(this.lengthNumbers, sehSimPhonebookResponse.lengthNumbers) && HidlSupport.deepEquals(this.dataTypeNumbers, sehSimPhonebookResponse.dataTypeNumbers) && HidlSupport.deepEquals(this.numbers, sehSimPhonebookResponse.numbers) && this.recordIndex == sehSimPhonebookResponse.recordIndex && this.nextIndex == sehSimPhonebookResponse.nextIndex;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.lengthAlphas)), Integer.valueOf(HidlSupport.deepHashCode(this.dataTypeAlphas)), Integer.valueOf(HidlSupport.deepHashCode(this.alphaTags)), Integer.valueOf(HidlSupport.deepHashCode(this.lengthNumbers)), Integer.valueOf(HidlSupport.deepHashCode(this.dataTypeNumbers)), Integer.valueOf(HidlSupport.deepHashCode(this.numbers)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.recordIndex))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nextIndex))));
    }

    public final String toString() {
        return "{.lengthAlphas = " + this.lengthAlphas + ", .dataTypeAlphas = " + this.dataTypeAlphas + ", .alphaTags = " + this.alphaTags + ", .lengthNumbers = " + this.lengthNumbers + ", .dataTypeNumbers = " + this.dataTypeNumbers + ", .numbers = " + this.numbers + ", .recordIndex = " + this.recordIndex + ", .nextIndex = " + this.nextIndex + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(104L), 0L);
    }

    public static final ArrayList<SehSimPhonebookResponse> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehSimPhonebookResponse> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 104, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehSimPhonebookResponse sehSimPhonebookResponse = new SehSimPhonebookResponse();
            sehSimPhonebookResponse.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 104);
            arrayList.add(sehSimPhonebookResponse);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(j + 8);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j, true);
        this.lengthAlphas.clear();
        for (int i = 0; i < int32; i++) {
            this.lengthAlphas.add(Integer.valueOf(embeddedBuffer.getInt32(i * 4)));
        }
        int int322 = hwBlob.getInt32(j + 24);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 4, hwBlob.handle(), j + 16, true);
        this.dataTypeAlphas.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            this.dataTypeAlphas.add(Integer.valueOf(embeddedBuffer2.getInt32(i2 * 4)));
        }
        int int323 = hwBlob.getInt32(j + 40);
        HwBlob embeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 16, hwBlob.handle(), j + 32, true);
        this.alphaTags.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            new String();
            int i4 = i3 * 16;
            String string = embeddedBuffer3.getString(i4);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, embeddedBuffer3.handle(), i4, false);
            this.alphaTags.add(string);
        }
        int int324 = hwBlob.getInt32(j + 56);
        HwBlob embeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 4, hwBlob.handle(), j + 48, true);
        this.lengthNumbers.clear();
        for (int i5 = 0; i5 < int324; i5++) {
            this.lengthNumbers.add(Integer.valueOf(embeddedBuffer4.getInt32(i5 * 4)));
        }
        int int325 = hwBlob.getInt32(j + 72);
        HwBlob embeddedBuffer5 = hwParcel.readEmbeddedBuffer(int325 * 4, hwBlob.handle(), j + 64, true);
        this.dataTypeNumbers.clear();
        for (int i6 = 0; i6 < int325; i6++) {
            this.dataTypeNumbers.add(Integer.valueOf(embeddedBuffer5.getInt32(i6 * 4)));
        }
        int int326 = hwBlob.getInt32(j + 88);
        HwBlob embeddedBuffer6 = hwParcel.readEmbeddedBuffer(int326 * 16, hwBlob.handle(), j + 80, true);
        this.numbers.clear();
        for (int i7 = 0; i7 < int326; i7++) {
            new String();
            int i8 = i7 * 16;
            String string2 = embeddedBuffer6.getString(i8);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, embeddedBuffer6.handle(), i8, false);
            this.numbers.add(string2);
        }
        this.recordIndex = hwBlob.getInt32(j + 96);
        this.nextIndex = hwBlob.getInt32(j + 100);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(104);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehSimPhonebookResponse> arrayList) {
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
        int size = this.lengthAlphas.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.lengthAlphas.get(i).intValue());
        }
        hwBlob.putBlob(j, hwBlob2);
        int size2 = this.dataTypeAlphas.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size2);
        hwBlob.putBool(28 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 4);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putInt32(i2 * 4, this.dataTypeAlphas.get(i2).intValue());
        }
        hwBlob.putBlob(j2, hwBlob3);
        int size3 = this.alphaTags.size();
        long j3 = 32 + j;
        hwBlob.putInt32(40 + j, size3);
        hwBlob.putBool(44 + j, false);
        HwBlob hwBlob4 = new HwBlob(size3 * 16);
        for (int i3 = 0; i3 < size3; i3++) {
            hwBlob4.putString(i3 * 16, this.alphaTags.get(i3));
        }
        hwBlob.putBlob(j3, hwBlob4);
        int size4 = this.lengthNumbers.size();
        long j4 = 48 + j;
        hwBlob.putInt32(56 + j, size4);
        hwBlob.putBool(60 + j, false);
        HwBlob hwBlob5 = new HwBlob(size4 * 4);
        for (int i4 = 0; i4 < size4; i4++) {
            hwBlob5.putInt32(i4 * 4, this.lengthNumbers.get(i4).intValue());
        }
        hwBlob.putBlob(j4, hwBlob5);
        int size5 = this.dataTypeNumbers.size();
        long j5 = 64 + j;
        hwBlob.putInt32(72 + j, size5);
        hwBlob.putBool(76 + j, false);
        HwBlob hwBlob6 = new HwBlob(size5 * 4);
        for (int i5 = 0; i5 < size5; i5++) {
            hwBlob6.putInt32(i5 * 4, this.dataTypeNumbers.get(i5).intValue());
        }
        hwBlob.putBlob(j5, hwBlob6);
        int size6 = this.numbers.size();
        long j6 = 80 + j;
        hwBlob.putInt32(88 + j, size6);
        hwBlob.putBool(92 + j, false);
        HwBlob hwBlob7 = new HwBlob(size6 * 16);
        for (int i6 = 0; i6 < size6; i6++) {
            hwBlob7.putString(i6 * 16, this.numbers.get(i6));
        }
        hwBlob.putBlob(j6, hwBlob7);
        hwBlob.putInt32(96 + j, this.recordIndex);
        hwBlob.putInt32(j + 100, this.nextIndex);
    }
}
