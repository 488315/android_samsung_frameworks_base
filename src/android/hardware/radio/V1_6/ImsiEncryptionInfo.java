package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ImsiEncryptionInfo {
    public android.hardware.radio.V1_1.ImsiEncryptionInfo base = new android.hardware.radio.V1_1.ImsiEncryptionInfo();
    public byte keyType = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ImsiEncryptionInfo.class) {
            return false;
        }
        ImsiEncryptionInfo imsiEncryptionInfo = (ImsiEncryptionInfo) obj;
        return HidlSupport.deepEquals(this.base, imsiEncryptionInfo.base) && this.keyType == imsiEncryptionInfo.keyType;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.keyType))));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .keyType = " + PublicKeyType.toString(this.keyType) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final ArrayList<ImsiEncryptionInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ImsiEncryptionInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ImsiEncryptionInfo imsiEncryptionInfo = new ImsiEncryptionInfo();
            imsiEncryptionInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 80);
            arrayList.add(imsiEncryptionInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.keyType = hwBlob.getInt8(j + 72);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ImsiEncryptionInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt8(j + 72, this.keyType);
    }
}
