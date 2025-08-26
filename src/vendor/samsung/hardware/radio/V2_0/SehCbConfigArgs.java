package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCbConfigArgs {
    public int enabled = 0;
    public int selectedId = 0;
    public int msgIdMaxCount = 0;
    public int msgIdCount = 0;
    public String msgIDs = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehCbConfigArgs.class) {
            return false;
        }
        SehCbConfigArgs sehCbConfigArgs = (SehCbConfigArgs) obj;
        return this.enabled == sehCbConfigArgs.enabled && this.selectedId == sehCbConfigArgs.selectedId && this.msgIdMaxCount == sehCbConfigArgs.msgIdMaxCount && this.msgIdCount == sehCbConfigArgs.msgIdCount && HidlSupport.deepEquals(this.msgIDs, sehCbConfigArgs.msgIDs);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.enabled))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.selectedId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.msgIdMaxCount))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.msgIdCount))), Integer.valueOf(HidlSupport.deepHashCode(this.msgIDs)));
    }

    public final String toString() {
        return "{.enabled = " + this.enabled + ", .selectedId = " + this.selectedId + ", .msgIdMaxCount = " + this.msgIdMaxCount + ", .msgIdCount = " + this.msgIdCount + ", .msgIDs = " + this.msgIDs + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SehCbConfigArgs> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehCbConfigArgs> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehCbConfigArgs sehCbConfigArgs = new SehCbConfigArgs();
            sehCbConfigArgs.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(sehCbConfigArgs);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.enabled = hwBlob.getInt32(j);
        this.selectedId = hwBlob.getInt32(4 + j);
        this.msgIdMaxCount = hwBlob.getInt32(8 + j);
        this.msgIdCount = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        this.msgIDs = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehCbConfigArgs> arrayList) {
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
        hwBlob.putInt32(j, this.enabled);
        hwBlob.putInt32(4 + j, this.selectedId);
        hwBlob.putInt32(8 + j, this.msgIdMaxCount);
        hwBlob.putInt32(12 + j, this.msgIdCount);
        hwBlob.putString(j + 16, this.msgIDs);
    }
}
