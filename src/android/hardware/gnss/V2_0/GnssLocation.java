package android.hardware.gnss.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GnssLocation {
    public android.hardware.gnss.V1_0.GnssLocation v1_0 = new android.hardware.gnss.V1_0.GnssLocation();
    public ElapsedRealtime elapsedRealtime = new ElapsedRealtime();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != GnssLocation.class) {
            return false;
        }
        GnssLocation gnssLocation = (GnssLocation) obj;
        return HidlSupport.deepEquals(this.v1_0, gnssLocation.v1_0) && HidlSupport.deepEquals(this.elapsedRealtime, gnssLocation.elapsedRealtime);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.v1_0)), Integer.valueOf(HidlSupport.deepHashCode(this.elapsedRealtime)));
    }

    public final String toString() {
        return "{.v1_0 = " + this.v1_0 + ", .elapsedRealtime = " + this.elapsedRealtime + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<GnssLocation> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GnssLocation> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            GnssLocation gnssLocation = new GnssLocation();
            gnssLocation.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(gnssLocation);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.v1_0.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.elapsedRealtime.readEmbeddedFromParcel(hwParcel, hwBlob, j + 64);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssLocation> arrayList) {
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
        this.v1_0.writeEmbeddedToBlob(hwBlob, j);
        this.elapsedRealtime.writeEmbeddedToBlob(hwBlob, j + 64);
    }
}
