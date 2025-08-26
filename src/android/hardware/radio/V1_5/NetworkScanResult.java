package android.hardware.radio.V1_5;

import android.hardware.radio.V1_0.RadioError;
import android.hardware.radio.V1_1.ScanStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class NetworkScanResult {
    public int status = 0;
    public int error = 0;
    public ArrayList<CellInfo> networkInfos = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NetworkScanResult.class) {
            return false;
        }
        NetworkScanResult networkScanResult = (NetworkScanResult) obj;
        return this.status == networkScanResult.status && this.error == networkScanResult.error && HidlSupport.deepEquals(this.networkInfos, networkScanResult.networkInfos);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.error))), Integer.valueOf(HidlSupport.deepHashCode(this.networkInfos)));
    }

    public final String toString() {
        return "{.status = " + ScanStatus.toString(this.status) + ", .error = " + RadioError.toString(this.error) + ", .networkInfos = " + this.networkInfos + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<NetworkScanResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NetworkScanResult> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NetworkScanResult networkScanResult = new NetworkScanResult();
            networkScanResult.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
            arrayList.add(networkScanResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j);
        this.error = hwBlob.getInt32(4 + j);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j + 16);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 216, hwBlob.handle(), j2, true);
        this.networkInfos.clear();
        for (int i = 0; i < int32; i++) {
            CellInfo cellInfo = new CellInfo();
            cellInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 216);
            this.networkInfos.add(cellInfo);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NetworkScanResult> arrayList) {
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
        hwBlob.putInt32(j, this.status);
        hwBlob.putInt32(4 + j, this.error);
        int size = this.networkInfos.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(j + 20, false);
        HwBlob hwBlob2 = new HwBlob(size * 216);
        for (int i = 0; i < size; i++) {
            this.networkInfos.get(i).writeEmbeddedToBlob(hwBlob2, i * 216);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
