package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class IccIo {
    public int command = 0;
    public int fileId = 0;
    public String path = new String();
    public int p1 = 0;
    public int p2 = 0;
    public int p3 = 0;
    public String data = new String();
    public String pin2 = new String();
    public String aid = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != IccIo.class) {
            return false;
        }
        IccIo iccIo = (IccIo) obj;
        return this.command == iccIo.command && this.fileId == iccIo.fileId && HidlSupport.deepEquals(this.path, iccIo.path) && this.p1 == iccIo.p1 && this.p2 == iccIo.p2 && this.p3 == iccIo.p3 && HidlSupport.deepEquals(this.data, iccIo.data) && HidlSupport.deepEquals(this.pin2, iccIo.pin2) && HidlSupport.deepEquals(this.aid, iccIo.aid);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.command))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.fileId))), Integer.valueOf(HidlSupport.deepHashCode(this.path)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p1))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p2))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p3))), Integer.valueOf(HidlSupport.deepHashCode(this.data)), Integer.valueOf(HidlSupport.deepHashCode(this.pin2)), Integer.valueOf(HidlSupport.deepHashCode(this.aid)));
    }

    public final String toString() {
        return "{.command = " + this.command + ", .fileId = " + this.fileId + ", .path = " + this.path + ", .p1 = " + this.p1 + ", .p2 = " + this.p2 + ", .p3 = " + this.p3 + ", .data = " + this.data + ", .pin2 = " + this.pin2 + ", .aid = " + this.aid + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<IccIo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<IccIo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            IccIo iccIo = new IccIo();
            iccIo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            arrayList.add(iccIo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.command = hwBlob.getInt32(j);
        this.fileId = hwBlob.getInt32(j + 4);
        long j2 = j + 8;
        this.path = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.p1 = hwBlob.getInt32(j + 24);
        this.p2 = hwBlob.getInt32(j + 28);
        this.p3 = hwBlob.getInt32(j + 32);
        long j3 = j + 40;
        this.data = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        long j4 = j + 56;
        this.pin2 = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j4, false);
        long j5 = j + 72;
        this.aid = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j5, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<IccIo> arrayList) {
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
        hwBlob.putInt32(j, this.command);
        hwBlob.putInt32(4 + j, this.fileId);
        hwBlob.putString(8 + j, this.path);
        hwBlob.putInt32(24 + j, this.p1);
        hwBlob.putInt32(28 + j, this.p2);
        hwBlob.putInt32(32 + j, this.p3);
        hwBlob.putString(40 + j, this.data);
        hwBlob.putString(56 + j, this.pin2);
        hwBlob.putString(j + 72, this.aid);
    }
}
