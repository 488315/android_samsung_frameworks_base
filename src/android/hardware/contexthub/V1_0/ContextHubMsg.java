package android.hardware.contexthub.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ContextHubMsg {
    public long appName = 0;
    public short hostEndPoint = 0;
    public int msgType = 0;
    public ArrayList<Byte> msg = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ContextHubMsg.class) {
            return false;
        }
        ContextHubMsg contextHubMsg = (ContextHubMsg) obj;
        return this.appName == contextHubMsg.appName && this.hostEndPoint == contextHubMsg.hostEndPoint && this.msgType == contextHubMsg.msgType && HidlSupport.deepEquals(this.msg, contextHubMsg.msg);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.appName))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.hostEndPoint))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.msgType))), Integer.valueOf(HidlSupport.deepHashCode(this.msg)));
    }

    public final String toString() {
        return "{.appName = " + this.appName + ", .hostEndPoint = " + ((int) this.hostEndPoint) + ", .msgType = " + this.msgType + ", .msg = " + this.msg + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<ContextHubMsg> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ContextHubMsg> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ContextHubMsg contextHubMsg = new ContextHubMsg();
            contextHubMsg.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(contextHubMsg);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.appName = hwBlob.getInt64(j);
        this.hostEndPoint = hwBlob.getInt16(8 + j);
        this.msgType = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(j + 24);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j2, true);
        this.msg.clear();
        for (int i = 0; i < int32; i++) {
            this.msg.add(Byte.valueOf(embeddedBuffer.getInt8(i)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ContextHubMsg> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt64(j, this.appName);
        hwBlob.putInt16(8 + j, this.hostEndPoint);
        hwBlob.putInt32(12 + j, this.msgType);
        int size = this.msg.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size);
        hwBlob.putBool(j + 28, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.msg.get(i).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
