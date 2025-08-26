package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehAdnRecord {
    public ArrayList<Byte> name = new ArrayList<>();
    public int nameDcs = 0;
    public int nameLength = 0;
    public String number = new String();
    public ArrayList<Byte> gsm8bitEmail = new ArrayList<>();
    public int gsm8bitEmailLength = 0;
    public String anr = new String();
    public String anrA = new String();
    public String anrB = new String();
    public String anrC = new String();
    public ArrayList<Byte> sne = new ArrayList<>();
    public int sneLength = 0;
    public int sneDcs = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehAdnRecord.class) {
            return false;
        }
        SehAdnRecord sehAdnRecord = (SehAdnRecord) obj;
        return HidlSupport.deepEquals(this.name, sehAdnRecord.name) && this.nameDcs == sehAdnRecord.nameDcs && this.nameLength == sehAdnRecord.nameLength && HidlSupport.deepEquals(this.number, sehAdnRecord.number) && HidlSupport.deepEquals(this.gsm8bitEmail, sehAdnRecord.gsm8bitEmail) && this.gsm8bitEmailLength == sehAdnRecord.gsm8bitEmailLength && HidlSupport.deepEquals(this.anr, sehAdnRecord.anr) && HidlSupport.deepEquals(this.anrA, sehAdnRecord.anrA) && HidlSupport.deepEquals(this.anrB, sehAdnRecord.anrB) && HidlSupport.deepEquals(this.anrC, sehAdnRecord.anrC) && HidlSupport.deepEquals(this.sne, sehAdnRecord.sne) && this.sneLength == sehAdnRecord.sneLength && this.sneDcs == sehAdnRecord.sneDcs;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nameDcs))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nameLength))), Integer.valueOf(HidlSupport.deepHashCode(this.number)), Integer.valueOf(HidlSupport.deepHashCode(this.gsm8bitEmail)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.gsm8bitEmailLength))), Integer.valueOf(HidlSupport.deepHashCode(this.anr)), Integer.valueOf(HidlSupport.deepHashCode(this.anrA)), Integer.valueOf(HidlSupport.deepHashCode(this.anrB)), Integer.valueOf(HidlSupport.deepHashCode(this.anrC)), Integer.valueOf(HidlSupport.deepHashCode(this.sne)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sneLength))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sneDcs))));
    }

    public final String toString() {
        return "{.name = " + this.name + ", .nameDcs = " + this.nameDcs + ", .nameLength = " + this.nameLength + ", .number = " + this.number + ", .gsm8bitEmail = " + this.gsm8bitEmail + ", .gsm8bitEmailLength = " + this.gsm8bitEmailLength + ", .anr = " + this.anr + ", .anrA = " + this.anrA + ", .anrB = " + this.anrB + ", .anrC = " + this.anrC + ", .sne = " + this.sne + ", .sneLength = " + this.sneLength + ", .sneDcs = " + this.sneDcs + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(152L), 0L);
    }

    public static final ArrayList<SehAdnRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehAdnRecord> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 152, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehAdnRecord sehAdnRecord = new SehAdnRecord();
            sehAdnRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 152);
            arrayList.add(sehAdnRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(8 + j);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j, true);
        this.name.clear();
        for (int i = 0; i < int32; i++) {
            this.name.add(Byte.valueOf(embeddedBuffer.getInt8(i)));
        }
        this.nameDcs = hwBlob.getInt32(16 + j);
        this.nameLength = hwBlob.getInt32(20 + j);
        long j2 = j + 24;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r11.getBytes().length + 1, hwBlob.handle(), j2, false);
        int int322 = hwBlob.getInt32(48 + j);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322, hwBlob.handle(), j + 40, true);
        this.gsm8bitEmail.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            this.gsm8bitEmail.add(Byte.valueOf(embeddedBuffer2.getInt8(i2)));
        }
        this.gsm8bitEmailLength = hwBlob.getInt32(56 + j);
        long j3 = j + 64;
        this.anr = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r11.getBytes().length + 1, hwBlob.handle(), j3, false);
        long j4 = j + 80;
        this.anrA = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(r11.getBytes().length + 1, hwBlob.handle(), j4, false);
        long j5 = j + 96;
        this.anrB = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(r11.getBytes().length + 1, hwBlob.handle(), j5, false);
        long j6 = j + 112;
        this.anrC = hwBlob.getString(j6);
        hwParcel.readEmbeddedBuffer(r11.getBytes().length + 1, hwBlob.handle(), j6, false);
        int int323 = hwBlob.getInt32(136 + j);
        HwBlob embeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323, hwBlob.handle(), j + 128, true);
        this.sne.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            this.sne.add(Byte.valueOf(embeddedBuffer3.getInt8(i3)));
        }
        this.sneLength = hwBlob.getInt32(144 + j);
        this.sneDcs = hwBlob.getInt32(j + 148);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(152);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehAdnRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 152);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 152);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        int size = this.name.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.name.get(i).byteValue());
        }
        hwBlob.putBlob(j, hwBlob2);
        hwBlob.putInt32(16 + j, this.nameDcs);
        hwBlob.putInt32(20 + j, this.nameLength);
        hwBlob.putString(24 + j, this.number);
        int size2 = this.gsm8bitEmail.size();
        long j2 = 40 + j;
        hwBlob.putInt32(48 + j, size2);
        hwBlob.putBool(52 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putInt8(i2, this.gsm8bitEmail.get(i2).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob3);
        hwBlob.putInt32(56 + j, this.gsm8bitEmailLength);
        hwBlob.putString(64 + j, this.anr);
        hwBlob.putString(80 + j, this.anrA);
        hwBlob.putString(96 + j, this.anrB);
        hwBlob.putString(112 + j, this.anrC);
        int size3 = this.sne.size();
        long j3 = 128 + j;
        hwBlob.putInt32(136 + j, size3);
        hwBlob.putBool(140 + j, false);
        HwBlob hwBlob4 = new HwBlob(size3);
        for (int i3 = 0; i3 < size3; i3++) {
            hwBlob4.putInt8(i3, this.sne.get(i3).byteValue());
        }
        hwBlob.putBlob(j3, hwBlob4);
        hwBlob.putInt32(144 + j, this.sneLength);
        hwBlob.putInt32(j + 148, this.sneDcs);
    }
}
