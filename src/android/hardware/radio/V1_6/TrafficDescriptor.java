package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class TrafficDescriptor {
    public OptionalDnn dnn = new OptionalDnn();
    public OptionalOsAppId osAppId = new OptionalOsAppId();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != TrafficDescriptor.class) {
            return false;
        }
        TrafficDescriptor trafficDescriptor = (TrafficDescriptor) obj;
        return HidlSupport.deepEquals(this.dnn, trafficDescriptor.dnn) && HidlSupport.deepEquals(this.osAppId, trafficDescriptor.osAppId);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.dnn)), Integer.valueOf(HidlSupport.deepHashCode(this.osAppId)));
    }

    public final String toString() {
        return "{.dnn = " + this.dnn + ", .osAppId = " + this.osAppId + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<TrafficDescriptor> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<TrafficDescriptor> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            TrafficDescriptor trafficDescriptor = new TrafficDescriptor();
            trafficDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(trafficDescriptor);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.dnn.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.osAppId.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<TrafficDescriptor> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.dnn.writeEmbeddedToBlob(hwBlob, j);
        this.osAppId.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
