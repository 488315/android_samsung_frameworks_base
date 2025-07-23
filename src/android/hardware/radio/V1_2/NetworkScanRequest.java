package android.hardware.radio.V1_2;

import android.hardware.radio.V1_1.RadioAccessSpecifier;
import android.hardware.radio.V1_1.ScanType;
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
    public int maxSearchTime = 0;
    public boolean incrementalResults = false;
    public int incrementalResultsPeriodicity = 0;
    public ArrayList<String> mccMncs = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NetworkScanRequest.class) {
            return false;
        }
        NetworkScanRequest networkScanRequest = (NetworkScanRequest) obj;
        return this.type == networkScanRequest.type && this.interval == networkScanRequest.interval && HidlSupport.deepEquals(this.specifiers, networkScanRequest.specifiers) && this.maxSearchTime == networkScanRequest.maxSearchTime && this.incrementalResults == networkScanRequest.incrementalResults && this.incrementalResultsPeriodicity == networkScanRequest.incrementalResultsPeriodicity && HidlSupport.deepEquals(this.mccMncs, networkScanRequest.mccMncs);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.interval))), Integer.valueOf(HidlSupport.deepHashCode(this.specifiers)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxSearchTime))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.incrementalResults))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.incrementalResultsPeriodicity))), Integer.valueOf(HidlSupport.deepHashCode(this.mccMncs)));
    }

    public final String toString() {
        return "{.type = " + ScanType.toString(this.type) + ", .interval = " + this.interval + ", .specifiers = " + this.specifiers + ", .maxSearchTime = " + this.maxSearchTime + ", .incrementalResults = " + this.incrementalResults + ", .incrementalResultsPeriodicity = " + this.incrementalResultsPeriodicity + ", .mccMncs = " + this.mccMncs + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<NetworkScanRequest> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NetworkScanRequest> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NetworkScanRequest networkScanRequest = new NetworkScanRequest();
            networkScanRequest.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(networkScanRequest);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        this.interval = hwBlob.getInt32(4 + j);
        int int32 = hwBlob.getInt32(16 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, hwBlob.handle(), j + 8, true);
        this.specifiers.clear();
        for (int i = 0; i < int32; i++) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            this.specifiers.add(radioAccessSpecifier);
        }
        this.maxSearchTime = hwBlob.getInt32(24 + j);
        this.incrementalResults = hwBlob.getBool(28 + j);
        this.incrementalResultsPeriodicity = hwBlob.getInt32(32 + j);
        int int322 = hwBlob.getInt32(j + 48);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j + 40, true);
        this.mccMncs.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new String();
            int i3 = i2 * 16;
            String string = readEmbeddedBuffer2.getString(i3);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer2.handle(), i3, false);
            this.mccMncs.add(string);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NetworkScanRequest> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
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
        hwBlob.putBool(20 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            this.specifiers.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putInt32(24 + j, this.maxSearchTime);
        hwBlob.putBool(28 + j, this.incrementalResults);
        hwBlob.putInt32(32 + j, this.incrementalResultsPeriodicity);
        int size2 = this.mccMncs.size();
        long j3 = 40 + j;
        hwBlob.putInt32(48 + j, size2);
        hwBlob.putBool(j + 52, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.mccMncs.get(i2));
        }
        hwBlob.putBlob(j3, hwBlob3);
    }
}
