package android.hardware.contexthub.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class HubAppInfo {
    public long appId = 0;
    public int version = 0;
    public ArrayList<MemRange> memUsage = new ArrayList<>();
    public boolean enabled = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != HubAppInfo.class) {
            return false;
        }
        HubAppInfo hubAppInfo = (HubAppInfo) obj;
        return this.appId == hubAppInfo.appId && this.version == hubAppInfo.version && HidlSupport.deepEquals(this.memUsage, hubAppInfo.memUsage) && this.enabled == hubAppInfo.enabled;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.appId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.version))), Integer.valueOf(HidlSupport.deepHashCode(this.memUsage)), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.enabled))));
    }

    public final String toString() {
        return "{.appId = " + this.appId + ", .version = " + this.version + ", .memUsage = " + this.memUsage + ", .enabled = " + this.enabled + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<HubAppInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<HubAppInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            HubAppInfo hubAppInfo = new HubAppInfo();
            hubAppInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(hubAppInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.appId = hwBlob.getInt64(j);
        this.version = hwBlob.getInt32(8 + j);
        int int32 = hwBlob.getInt32(24 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j + 16, true);
        this.memUsage.clear();
        for (int i = 0; i < int32; i++) {
            MemRange memRange = new MemRange();
            memRange.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            this.memUsage.add(memRange);
        }
        this.enabled = hwBlob.getBool(j + 32);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<HubAppInfo> arrayList) {
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
        hwBlob.putInt64(j, this.appId);
        hwBlob.putInt32(8 + j, this.version);
        int size = this.memUsage.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size);
        hwBlob.putBool(28 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            this.memUsage.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putBool(j + 32, this.enabled);
    }
}
