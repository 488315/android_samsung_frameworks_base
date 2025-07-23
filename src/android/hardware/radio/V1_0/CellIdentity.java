package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentity {
    public int cellInfoType = 0;
    public ArrayList<CellIdentityGsm> cellIdentityGsm = new ArrayList<>();
    public ArrayList<CellIdentityWcdma> cellIdentityWcdma = new ArrayList<>();
    public ArrayList<CellIdentityCdma> cellIdentityCdma = new ArrayList<>();
    public ArrayList<CellIdentityLte> cellIdentityLte = new ArrayList<>();
    public ArrayList<CellIdentityTdscdma> cellIdentityTdscdma = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentity.class) {
            return false;
        }
        CellIdentity cellIdentity = (CellIdentity) obj;
        return this.cellInfoType == cellIdentity.cellInfoType && HidlSupport.deepEquals(this.cellIdentityGsm, cellIdentity.cellIdentityGsm) && HidlSupport.deepEquals(this.cellIdentityWcdma, cellIdentity.cellIdentityWcdma) && HidlSupport.deepEquals(this.cellIdentityCdma, cellIdentity.cellIdentityCdma) && HidlSupport.deepEquals(this.cellIdentityLte, cellIdentity.cellIdentityLte) && HidlSupport.deepEquals(this.cellIdentityTdscdma, cellIdentity.cellIdentityTdscdma);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cellInfoType))), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityGsm)), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityWcdma)), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityCdma)), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityLte)), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityTdscdma)));
    }

    public final String toString() {
        return "{.cellInfoType = " + CellInfoType.toString(this.cellInfoType) + ", .cellIdentityGsm = " + this.cellIdentityGsm + ", .cellIdentityWcdma = " + this.cellIdentityWcdma + ", .cellIdentityCdma = " + this.cellIdentityCdma + ", .cellIdentityLte = " + this.cellIdentityLte + ", .cellIdentityTdscdma = " + this.cellIdentityTdscdma + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<CellIdentity> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentity> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentity cellIdentity = new CellIdentity();
            cellIdentity.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(cellIdentity);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cellInfoType = hwBlob.getInt32(j);
        int int32 = hwBlob.getInt32(16 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, hwBlob.handle(), j + 8, true);
        this.cellIdentityGsm.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityGsm cellIdentityGsm = new CellIdentityGsm();
            cellIdentityGsm.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            this.cellIdentityGsm.add(cellIdentityGsm);
        }
        int int322 = hwBlob.getInt32(32 + j);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 48, hwBlob.handle(), j + 24, true);
        this.cellIdentityWcdma.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            CellIdentityWcdma cellIdentityWcdma = new CellIdentityWcdma();
            cellIdentityWcdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 48);
            this.cellIdentityWcdma.add(cellIdentityWcdma);
        }
        int int323 = hwBlob.getInt32(48 + j);
        HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 20, hwBlob.handle(), j + 40, true);
        this.cellIdentityCdma.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            CellIdentityCdma cellIdentityCdma = new CellIdentityCdma();
            cellIdentityCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, i3 * 20);
            this.cellIdentityCdma.add(cellIdentityCdma);
        }
        int int324 = hwBlob.getInt32(64 + j);
        HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 48, hwBlob.handle(), j + 56, true);
        this.cellIdentityLte.clear();
        for (int i4 = 0; i4 < int324; i4++) {
            CellIdentityLte cellIdentityLte = new CellIdentityLte();
            cellIdentityLte.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer4, i4 * 48);
            this.cellIdentityLte.add(cellIdentityLte);
        }
        int int325 = hwBlob.getInt32(j + 80);
        HwBlob readEmbeddedBuffer5 = hwParcel.readEmbeddedBuffer(int325 * 48, hwBlob.handle(), j + 72, true);
        this.cellIdentityTdscdma.clear();
        for (int i5 = 0; i5 < int325; i5++) {
            CellIdentityTdscdma cellIdentityTdscdma = new CellIdentityTdscdma();
            cellIdentityTdscdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer5, i5 * 48);
            this.cellIdentityTdscdma.add(cellIdentityTdscdma);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentity> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.cellInfoType);
        int size = this.cellIdentityGsm.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(20 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            this.cellIdentityGsm.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.cellIdentityWcdma.size();
        long j3 = 24 + j;
        hwBlob.putInt32(32 + j, size2);
        hwBlob.putBool(36 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 48);
        for (int i2 = 0; i2 < size2; i2++) {
            this.cellIdentityWcdma.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 48);
        }
        hwBlob.putBlob(j3, hwBlob3);
        int size3 = this.cellIdentityCdma.size();
        long j4 = 40 + j;
        hwBlob.putInt32(48 + j, size3);
        hwBlob.putBool(52 + j, false);
        HwBlob hwBlob4 = new HwBlob(size3 * 20);
        for (int i3 = 0; i3 < size3; i3++) {
            this.cellIdentityCdma.get(i3).writeEmbeddedToBlob(hwBlob4, i3 * 20);
        }
        hwBlob.putBlob(j4, hwBlob4);
        int size4 = this.cellIdentityLte.size();
        long j5 = 56 + j;
        hwBlob.putInt32(64 + j, size4);
        hwBlob.putBool(68 + j, false);
        HwBlob hwBlob5 = new HwBlob(size4 * 48);
        for (int i4 = 0; i4 < size4; i4++) {
            this.cellIdentityLte.get(i4).writeEmbeddedToBlob(hwBlob5, i4 * 48);
        }
        hwBlob.putBlob(j5, hwBlob5);
        int size5 = this.cellIdentityTdscdma.size();
        long j6 = 72 + j;
        hwBlob.putInt32(80 + j, size5);
        hwBlob.putBool(j + 84, false);
        HwBlob hwBlob6 = new HwBlob(size5 * 48);
        for (int i5 = 0; i5 < size5; i5++) {
            this.cellIdentityTdscdma.get(i5).writeEmbeddedToBlob(hwBlob6, i5 * 48);
        }
        hwBlob.putBlob(j6, hwBlob6);
    }
}
