package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SimApdu {
    public int sessionId = 0;
    public int cla = 0;
    public int instruction = 0;
    public int p1 = 0;
    public int p2 = 0;
    public int p3 = 0;
    public String data = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SimApdu.class) {
            return false;
        }
        SimApdu simApdu = (SimApdu) obj;
        return this.sessionId == simApdu.sessionId && this.cla == simApdu.cla && this.instruction == simApdu.instruction && this.p1 == simApdu.p1 && this.p2 == simApdu.p2 && this.p3 == simApdu.p3 && HidlSupport.deepEquals(this.data, simApdu.data);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sessionId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cla))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.instruction))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p1))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p2))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p3))), Integer.valueOf(HidlSupport.deepHashCode(this.data)));
    }

    public final String toString() {
        return "{.sessionId = " + this.sessionId + ", .cla = " + this.cla + ", .instruction = " + this.instruction + ", .p1 = " + this.p1 + ", .p2 = " + this.p2 + ", .p3 = " + this.p3 + ", .data = " + this.data + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<SimApdu> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SimApdu> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SimApdu simApdu = new SimApdu();
            simApdu.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(simApdu);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.sessionId = hwBlob.getInt32(j);
        this.cla = hwBlob.getInt32(4 + j);
        this.instruction = hwBlob.getInt32(8 + j);
        this.p1 = hwBlob.getInt32(12 + j);
        this.p2 = hwBlob.getInt32(16 + j);
        this.p3 = hwBlob.getInt32(20 + j);
        long j2 = j + 24;
        this.data = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SimApdu> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.sessionId);
        hwBlob.putInt32(4 + j, this.cla);
        hwBlob.putInt32(8 + j, this.instruction);
        hwBlob.putInt32(12 + j, this.p1);
        hwBlob.putInt32(16 + j, this.p2);
        hwBlob.putInt32(20 + j, this.p3);
        hwBlob.putString(j + 24, this.data);
    }
}
