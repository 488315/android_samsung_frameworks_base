package android.hardware.contexthub.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class HubAppInfo {
    public android.hardware.contexthub.V1_0.HubAppInfo info_1_0 = new android.hardware.contexthub.V1_0.HubAppInfo();
    public ArrayList<String> permissions = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != HubAppInfo.class) {
            return false;
        }
        HubAppInfo hubAppInfo = (HubAppInfo) obj;
        return HidlSupport.deepEquals(this.info_1_0, hubAppInfo.info_1_0) && HidlSupport.deepEquals(this.permissions, hubAppInfo.permissions);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.info_1_0)), Integer.valueOf(HidlSupport.deepHashCode(this.permissions)));
    }

    public final String toString() {
        return "{.info_1_0 = " + this.info_1_0 + ", .permissions = " + this.permissions + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<HubAppInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<HubAppInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            HubAppInfo hubAppInfo = new HubAppInfo();
            hubAppInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 56);
            arrayList.add(hubAppInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.info_1_0.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        long j2 = j + 40;
        int int32 = hwBlob.getInt32(j + 48);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2, true);
        this.permissions.clear();
        for (int i = 0; i < int32; i++) {
            new String();
            int i2 = i * 16;
            String string = embeddedBuffer.getString(i2);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, embeddedBuffer.handle(), i2, false);
            this.permissions.add(string);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<HubAppInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.info_1_0.writeEmbeddedToBlob(hwBlob, j);
        int size = this.permissions.size();
        long j2 = 40 + j;
        hwBlob.putInt32(48 + j, size);
        hwBlob.putBool(j + 52, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.permissions.get(i));
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
