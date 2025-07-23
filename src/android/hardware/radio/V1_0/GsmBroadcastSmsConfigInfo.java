package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GsmBroadcastSmsConfigInfo {
    public int fromServiceId = 0;
    public int toServiceId = 0;
    public int fromCodeScheme = 0;
    public int toCodeScheme = 0;
    public boolean selected = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != GsmBroadcastSmsConfigInfo.class) {
            return false;
        }
        GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = (GsmBroadcastSmsConfigInfo) obj;
        return this.fromServiceId == gsmBroadcastSmsConfigInfo.fromServiceId && this.toServiceId == gsmBroadcastSmsConfigInfo.toServiceId && this.fromCodeScheme == gsmBroadcastSmsConfigInfo.fromCodeScheme && this.toCodeScheme == gsmBroadcastSmsConfigInfo.toCodeScheme && this.selected == gsmBroadcastSmsConfigInfo.selected;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.fromServiceId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.toServiceId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.fromCodeScheme))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.toCodeScheme))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.selected))));
    }

    public final String toString() {
        return "{.fromServiceId = " + this.fromServiceId + ", .toServiceId = " + this.toServiceId + ", .fromCodeScheme = " + this.fromCodeScheme + ", .toCodeScheme = " + this.toCodeScheme + ", .selected = " + this.selected + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final ArrayList<GsmBroadcastSmsConfigInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GsmBroadcastSmsConfigInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = new GsmBroadcastSmsConfigInfo();
            gsmBroadcastSmsConfigInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            arrayList.add(gsmBroadcastSmsConfigInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.fromServiceId = hwBlob.getInt32(j);
        this.toServiceId = hwBlob.getInt32(4 + j);
        this.fromCodeScheme = hwBlob.getInt32(8 + j);
        this.toCodeScheme = hwBlob.getInt32(12 + j);
        this.selected = hwBlob.getBool(j + 16);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.fromServiceId);
        hwBlob.putInt32(4 + j, this.toServiceId);
        hwBlob.putInt32(8 + j, this.fromCodeScheme);
        hwBlob.putInt32(12 + j, this.toCodeScheme);
        hwBlob.putBool(j + 16, this.selected);
    }
}
