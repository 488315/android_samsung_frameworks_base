package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentityCdma {
    public int networkId = 0;
    public int systemId = 0;
    public int baseStationId = 0;
    public int longitude = 0;
    public int latitude = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CellIdentityCdma.class) {
            return false;
        }
        CellIdentityCdma cellIdentityCdma = (CellIdentityCdma) obj;
        return this.networkId == cellIdentityCdma.networkId && this.systemId == cellIdentityCdma.systemId && this.baseStationId == cellIdentityCdma.baseStationId && this.longitude == cellIdentityCdma.longitude && this.latitude == cellIdentityCdma.latitude;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.networkId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.systemId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.baseStationId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.longitude))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.latitude))));
    }

    public final String toString() {
        return "{.networkId = " + this.networkId + ", .systemId = " + this.systemId + ", .baseStationId = " + this.baseStationId + ", .longitude = " + this.longitude + ", .latitude = " + this.latitude + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final ArrayList<CellIdentityCdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityCdma> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellIdentityCdma cellIdentityCdma = new CellIdentityCdma();
            cellIdentityCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            arrayList.add(cellIdentityCdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.networkId = hwBlob.getInt32(j);
        this.systemId = hwBlob.getInt32(4 + j);
        this.baseStationId = hwBlob.getInt32(8 + j);
        this.longitude = hwBlob.getInt32(12 + j);
        this.latitude = hwBlob.getInt32(j + 16);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityCdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.networkId);
        hwBlob.putInt32(4 + j, this.systemId);
        hwBlob.putInt32(8 + j, this.baseStationId);
        hwBlob.putInt32(12 + j, this.longitude);
        hwBlob.putInt32(j + 16, this.latitude);
    }
}
