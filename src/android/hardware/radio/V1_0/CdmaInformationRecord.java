package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaInformationRecord {
    public int name = 0;
    public ArrayList<CdmaDisplayInfoRecord> display = new ArrayList<>();
    public ArrayList<CdmaNumberInfoRecord> number = new ArrayList<>();
    public ArrayList<CdmaSignalInfoRecord> signal = new ArrayList<>();
    public ArrayList<CdmaRedirectingNumberInfoRecord> redir = new ArrayList<>();
    public ArrayList<CdmaLineControlInfoRecord> lineCtrl = new ArrayList<>();
    public ArrayList<CdmaT53ClirInfoRecord> clir = new ArrayList<>();
    public ArrayList<CdmaT53AudioControlInfoRecord> audioCtrl = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaInformationRecord.class) {
            return false;
        }
        CdmaInformationRecord cdmaInformationRecord = (CdmaInformationRecord) obj;
        return this.name == cdmaInformationRecord.name && HidlSupport.deepEquals(this.display, cdmaInformationRecord.display) && HidlSupport.deepEquals(this.number, cdmaInformationRecord.number) && HidlSupport.deepEquals(this.signal, cdmaInformationRecord.signal) && HidlSupport.deepEquals(this.redir, cdmaInformationRecord.redir) && HidlSupport.deepEquals(this.lineCtrl, cdmaInformationRecord.lineCtrl) && HidlSupport.deepEquals(this.clir, cdmaInformationRecord.clir) && HidlSupport.deepEquals(this.audioCtrl, cdmaInformationRecord.audioCtrl);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.name))), Integer.valueOf(HidlSupport.deepHashCode(this.display)), Integer.valueOf(HidlSupport.deepHashCode(this.number)), Integer.valueOf(HidlSupport.deepHashCode(this.signal)), Integer.valueOf(HidlSupport.deepHashCode(this.redir)), Integer.valueOf(HidlSupport.deepHashCode(this.lineCtrl)), Integer.valueOf(HidlSupport.deepHashCode(this.clir)), Integer.valueOf(HidlSupport.deepHashCode(this.audioCtrl)));
    }

    public final String toString() {
        return "{.name = " + CdmaInfoRecName.toString(this.name) + ", .display = " + this.display + ", .number = " + this.number + ", .signal = " + this.signal + ", .redir = " + this.redir + ", .lineCtrl = " + this.lineCtrl + ", .clir = " + this.clir + ", .audioCtrl = " + this.audioCtrl + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final ArrayList<CdmaInformationRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaInformationRecord> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaInformationRecord cdmaInformationRecord = new CdmaInformationRecord();
            cdmaInformationRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 120);
            arrayList.add(cdmaInformationRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.name = hwBlob.getInt32(j);
        int int32 = hwBlob.getInt32(16 + j);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j + 8, true);
        this.display.clear();
        for (int i = 0; i < int32; i++) {
            CdmaDisplayInfoRecord cdmaDisplayInfoRecord = new CdmaDisplayInfoRecord();
            cdmaDisplayInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 16);
            this.display.add(cdmaDisplayInfoRecord);
        }
        int int322 = hwBlob.getInt32(32 + j);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 24, hwBlob.handle(), j + 24, true);
        this.number.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            CdmaNumberInfoRecord cdmaNumberInfoRecord = new CdmaNumberInfoRecord();
            cdmaNumberInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer2, i2 * 24);
            this.number.add(cdmaNumberInfoRecord);
        }
        int int323 = hwBlob.getInt32(48 + j);
        HwBlob embeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 4, hwBlob.handle(), j + 40, true);
        this.signal.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            CdmaSignalInfoRecord cdmaSignalInfoRecord = new CdmaSignalInfoRecord();
            cdmaSignalInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer3, i3 * 4);
            this.signal.add(cdmaSignalInfoRecord);
        }
        int int324 = hwBlob.getInt32(64 + j);
        HwBlob embeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 32, hwBlob.handle(), j + 56, true);
        this.redir.clear();
        for (int i4 = 0; i4 < int324; i4++) {
            CdmaRedirectingNumberInfoRecord cdmaRedirectingNumberInfoRecord = new CdmaRedirectingNumberInfoRecord();
            cdmaRedirectingNumberInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer4, i4 * 32);
            this.redir.add(cdmaRedirectingNumberInfoRecord);
        }
        int int325 = hwBlob.getInt32(80 + j);
        HwBlob embeddedBuffer5 = hwParcel.readEmbeddedBuffer(int325 * 4, hwBlob.handle(), j + 72, true);
        this.lineCtrl.clear();
        for (int i5 = 0; i5 < int325; i5++) {
            CdmaLineControlInfoRecord cdmaLineControlInfoRecord = new CdmaLineControlInfoRecord();
            cdmaLineControlInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer5, i5 * 4);
            this.lineCtrl.add(cdmaLineControlInfoRecord);
        }
        int int326 = hwBlob.getInt32(96 + j);
        HwBlob embeddedBuffer6 = hwParcel.readEmbeddedBuffer(int326, hwBlob.handle(), j + 88, true);
        this.clir.clear();
        for (int i6 = 0; i6 < int326; i6++) {
            CdmaT53ClirInfoRecord cdmaT53ClirInfoRecord = new CdmaT53ClirInfoRecord();
            cdmaT53ClirInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer6, i6);
            this.clir.add(cdmaT53ClirInfoRecord);
        }
        int int327 = hwBlob.getInt32(j + 112);
        HwBlob embeddedBuffer7 = hwParcel.readEmbeddedBuffer(int327 * 2, hwBlob.handle(), j + 104, true);
        this.audioCtrl.clear();
        for (int i7 = 0; i7 < int327; i7++) {
            CdmaT53AudioControlInfoRecord cdmaT53AudioControlInfoRecord = new CdmaT53AudioControlInfoRecord();
            cdmaT53AudioControlInfoRecord.readEmbeddedFromParcel(hwParcel, embeddedBuffer7, i7 * 2);
            this.audioCtrl.add(cdmaT53AudioControlInfoRecord);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaInformationRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.name);
        int size = this.display.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(20 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            this.display.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.number.size();
        long j3 = 24 + j;
        hwBlob.putInt32(32 + j, size2);
        hwBlob.putBool(36 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 24);
        for (int i2 = 0; i2 < size2; i2++) {
            this.number.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 24);
        }
        hwBlob.putBlob(j3, hwBlob3);
        int size3 = this.signal.size();
        long j4 = 40 + j;
        hwBlob.putInt32(48 + j, size3);
        hwBlob.putBool(52 + j, false);
        HwBlob hwBlob4 = new HwBlob(size3 * 4);
        for (int i3 = 0; i3 < size3; i3++) {
            this.signal.get(i3).writeEmbeddedToBlob(hwBlob4, i3 * 4);
        }
        hwBlob.putBlob(j4, hwBlob4);
        int size4 = this.redir.size();
        long j5 = 56 + j;
        hwBlob.putInt32(64 + j, size4);
        hwBlob.putBool(68 + j, false);
        HwBlob hwBlob5 = new HwBlob(size4 * 32);
        for (int i4 = 0; i4 < size4; i4++) {
            this.redir.get(i4).writeEmbeddedToBlob(hwBlob5, i4 * 32);
        }
        hwBlob.putBlob(j5, hwBlob5);
        int size5 = this.lineCtrl.size();
        long j6 = 72 + j;
        hwBlob.putInt32(80 + j, size5);
        hwBlob.putBool(84 + j, false);
        HwBlob hwBlob6 = new HwBlob(size5 * 4);
        for (int i5 = 0; i5 < size5; i5++) {
            this.lineCtrl.get(i5).writeEmbeddedToBlob(hwBlob6, i5 * 4);
        }
        hwBlob.putBlob(j6, hwBlob6);
        int size6 = this.clir.size();
        long j7 = 88 + j;
        hwBlob.putInt32(96 + j, size6);
        hwBlob.putBool(100 + j, false);
        HwBlob hwBlob7 = new HwBlob(size6);
        for (int i6 = 0; i6 < size6; i6++) {
            this.clir.get(i6).writeEmbeddedToBlob(hwBlob7, i6);
        }
        hwBlob.putBlob(j7, hwBlob7);
        int size7 = this.audioCtrl.size();
        long j8 = 104 + j;
        hwBlob.putInt32(112 + j, size7);
        hwBlob.putBool(j + 116, false);
        HwBlob hwBlob8 = new HwBlob(size7 * 2);
        for (int i7 = 0; i7 < size7; i7++) {
            this.audioCtrl.get(i7).writeEmbeddedToBlob(hwBlob8, i7 * 2);
        }
        hwBlob.putBlob(j8, hwBlob8);
    }
}
