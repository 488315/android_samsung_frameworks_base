package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaSmsWriteArgs {
    public int status = 0;
    public CdmaSmsMessage message = new CdmaSmsMessage();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaSmsWriteArgs.class) {
            return false;
        }
        CdmaSmsWriteArgs cdmaSmsWriteArgs = (CdmaSmsWriteArgs) obj;
        return this.status == cdmaSmsWriteArgs.status && HidlSupport.deepEquals(this.message, cdmaSmsWriteArgs.message);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(this.message)));
    }

    public final String toString() {
        return "{.status = " + CdmaSmsWriteArgsStatus.toString(this.status) + ", .message = " + this.message + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final ArrayList<CdmaSmsWriteArgs> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsWriteArgs> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaSmsWriteArgs cdmaSmsWriteArgs = new CdmaSmsWriteArgs();
            cdmaSmsWriteArgs.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(cdmaSmsWriteArgs);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j);
        this.message.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsWriteArgs> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.status);
        this.message.writeEmbeddedToBlob(hwBlob, j + 8);
    }
}
