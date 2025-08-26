package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class QosSession {
    public int qosSessionId = 0;
    public Qos qos = new Qos();
    public ArrayList<QosFilter> qosFilters = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != QosSession.class) {
            return false;
        }
        QosSession qosSession = (QosSession) obj;
        return this.qosSessionId == qosSession.qosSessionId && HidlSupport.deepEquals(this.qos, qosSession.qos) && HidlSupport.deepEquals(this.qosFilters, qosSession.qosFilters);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.qosSessionId))), Integer.valueOf(HidlSupport.deepHashCode(this.qos)), Integer.valueOf(HidlSupport.deepHashCode(this.qosFilters)));
    }

    public final String toString() {
        return "{.qosSessionId = " + this.qosSessionId + ", .qos = " + this.qos + ", .qosFilters = " + this.qosFilters + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<QosSession> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<QosSession> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            QosSession qosSession = new QosSession();
            qosSession.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 48);
            arrayList.add(qosSession);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.qosSessionId = hwBlob.getInt32(j);
        this.qos.readEmbeddedFromParcel(hwParcel, hwBlob, 4 + j);
        long j2 = j + 32;
        int int32 = hwBlob.getInt32(j + 40);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, hwBlob.handle(), j2, true);
        this.qosFilters.clear();
        for (int i = 0; i < int32; i++) {
            QosFilter qosFilter = new QosFilter();
            qosFilter.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            this.qosFilters.add(qosFilter);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<QosSession> arrayList) {
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
        hwBlob.putInt32(j, this.qosSessionId);
        this.qos.writeEmbeddedToBlob(hwBlob, 4 + j);
        int size = this.qosFilters.size();
        long j2 = 32 + j;
        hwBlob.putInt32(40 + j, size);
        hwBlob.putBool(j + 44, false);
        HwBlob hwBlob2 = new HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            this.qosFilters.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
