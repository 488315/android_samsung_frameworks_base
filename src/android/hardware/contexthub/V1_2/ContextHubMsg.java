package android.hardware.contexthub.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ContextHubMsg {
    public android.hardware.contexthub.V1_0.ContextHubMsg msg_1_0 = new android.hardware.contexthub.V1_0.ContextHubMsg();
    public ArrayList<String> permissions = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ContextHubMsg.class) {
            return false;
        }
        ContextHubMsg contextHubMsg = (ContextHubMsg) obj;
        return HidlSupport.deepEquals(this.msg_1_0, contextHubMsg.msg_1_0) && HidlSupport.deepEquals(this.permissions, contextHubMsg.permissions);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.msg_1_0)), Integer.valueOf(HidlSupport.deepHashCode(this.permissions)));
    }

    public final String toString() {
        return "{.msg_1_0 = " + this.msg_1_0 + ", .permissions = " + this.permissions + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<ContextHubMsg> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ContextHubMsg> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ContextHubMsg contextHubMsg = new ContextHubMsg();
            contextHubMsg.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(contextHubMsg);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.msg_1_0.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        long j2 = j + 32;
        int int32 = hwBlob.getInt32(j + 40);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2, true);
        this.permissions.clear();
        for (int i = 0; i < int32; i++) {
            new String();
            int i2 = i * 16;
            String string = readEmbeddedBuffer.getString(i2);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), i2, false);
            this.permissions.add(string);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ContextHubMsg> arrayList) {
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
        this.msg_1_0.writeEmbeddedToBlob(hwBlob, j);
        int size = this.permissions.size();
        long j2 = 32 + j;
        hwBlob.putInt32(40 + j, size);
        hwBlob.putBool(j + 44, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.permissions.get(i));
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
