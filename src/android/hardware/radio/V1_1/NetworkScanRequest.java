package android.hardware.radio.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class NetworkScanRequest {
    public int type = 0;
    public int interval = 0;
    public ArrayList<RadioAccessSpecifier> specifiers = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NetworkScanRequest.class) {
            return false;
        }
        NetworkScanRequest networkScanRequest = (NetworkScanRequest) obj;
        return this.type == networkScanRequest.type && this.interval == networkScanRequest.interval && HidlSupport.deepEquals(this.specifiers, networkScanRequest.specifiers);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.interval))), Integer.valueOf(HidlSupport.deepHashCode(this.specifiers)));
    }

    public final String toString() {
        return "{.type = " + ScanType.toString(this.type) + ", .interval = " + this.interval + ", .specifiers = " + this.specifiers + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<NetworkScanRequest> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NetworkScanRequest> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NetworkScanRequest networkScanRequest = new NetworkScanRequest();
            networkScanRequest.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(networkScanRequest);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        this.interval = hwBlob.getInt32(4 + j);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j + 16);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, hwBlob.handle(), j2, true);
        this.specifiers.clear();
        for (int i = 0; i < int32; i++) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            this.specifiers.add(radioAccessSpecifier);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NetworkScanRequest> arrayList) {
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
        hwBlob.putInt32(j, this.type);
        hwBlob.putInt32(4 + j, this.interval);
        int size = this.specifiers.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(j + 20, false);
        HwBlob hwBlob2 = new HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            this.specifiers.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
