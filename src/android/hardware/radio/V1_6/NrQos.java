package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class NrQos {
    public short fiveQi = 0;
    public QosBandwidth downlink = new QosBandwidth();
    public QosBandwidth uplink = new QosBandwidth();
    public byte qfi = 0;
    public short averagingWindowMs = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NrQos.class) {
            return false;
        }
        NrQos nrQos = (NrQos) obj;
        return this.fiveQi == nrQos.fiveQi && HidlSupport.deepEquals(this.downlink, nrQos.downlink) && HidlSupport.deepEquals(this.uplink, nrQos.uplink) && this.qfi == nrQos.qfi && this.averagingWindowMs == nrQos.averagingWindowMs;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.fiveQi))), Integer.valueOf(HidlSupport.deepHashCode(this.downlink)), Integer.valueOf(HidlSupport.deepHashCode(this.uplink)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.qfi))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.averagingWindowMs))));
    }

    public final String toString() {
        return "{.fiveQi = " + ((int) this.fiveQi) + ", .downlink = " + this.downlink + ", .uplink = " + this.uplink + ", .qfi = " + ((int) this.qfi) + ", .averagingWindowMs = " + ((int) this.averagingWindowMs) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<NrQos> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NrQos> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NrQos nrQos = new NrQos();
            nrQos.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(nrQos);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.fiveQi = hwBlob.getInt16(j);
        this.downlink.readEmbeddedFromParcel(hwParcel, hwBlob, 4 + j);
        this.uplink.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        this.qfi = hwBlob.getInt8(20 + j);
        this.averagingWindowMs = hwBlob.getInt16(j + 22);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NrQos> arrayList) {
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
        hwBlob.putInt16(j, this.fiveQi);
        this.downlink.writeEmbeddedToBlob(hwBlob, 4 + j);
        this.uplink.writeEmbeddedToBlob(hwBlob, 12 + j);
        hwBlob.putInt8(20 + j, this.qfi);
        hwBlob.putInt16(j + 22, this.averagingWindowMs);
    }
}
