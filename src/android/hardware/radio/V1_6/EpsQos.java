package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class EpsQos {
    public short qci = 0;
    public QosBandwidth downlink = new QosBandwidth();
    public QosBandwidth uplink = new QosBandwidth();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != EpsQos.class) {
            return false;
        }
        EpsQos epsQos = (EpsQos) obj;
        return this.qci == epsQos.qci && HidlSupport.deepEquals(this.downlink, epsQos.downlink) && HidlSupport.deepEquals(this.uplink, epsQos.uplink);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.qci))), Integer.valueOf(HidlSupport.deepHashCode(this.downlink)), Integer.valueOf(HidlSupport.deepHashCode(this.uplink)));
    }

    public final String toString() {
        return "{.qci = " + ((int) this.qci) + ", .downlink = " + this.downlink + ", .uplink = " + this.uplink + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final ArrayList<EpsQos> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<EpsQos> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            EpsQos epsQos = new EpsQos();
            epsQos.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 20);
            arrayList.add(epsQos);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.qci = hwBlob.getInt16(j);
        this.downlink.readEmbeddedFromParcel(hwParcel, hwBlob, 4 + j);
        this.uplink.readEmbeddedFromParcel(hwParcel, hwBlob, j + 12);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<EpsQos> arrayList) {
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
        hwBlob.putInt16(j, this.qci);
        this.downlink.writeEmbeddedToBlob(hwBlob, 4 + j);
        this.uplink.writeEmbeddedToBlob(hwBlob, j + 12);
    }
}
