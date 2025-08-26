package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ClosedSubscriberGroupInfo {
    public boolean csgIndication = false;
    public String homeNodebName = new String();
    public int csgIdentity = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ClosedSubscriberGroupInfo.class) {
            return false;
        }
        ClosedSubscriberGroupInfo closedSubscriberGroupInfo = (ClosedSubscriberGroupInfo) obj;
        return this.csgIndication == closedSubscriberGroupInfo.csgIndication && HidlSupport.deepEquals(this.homeNodebName, closedSubscriberGroupInfo.homeNodebName) && this.csgIdentity == closedSubscriberGroupInfo.csgIdentity;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.csgIndication))), Integer.valueOf(HidlSupport.deepHashCode(this.homeNodebName)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csgIdentity))));
    }

    public final String toString() {
        return "{.csgIndication = " + this.csgIndication + ", .homeNodebName = " + this.homeNodebName + ", .csgIdentity = " + this.csgIdentity + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<ClosedSubscriberGroupInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ClosedSubscriberGroupInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ClosedSubscriberGroupInfo closedSubscriberGroupInfo = new ClosedSubscriberGroupInfo();
            closedSubscriberGroupInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(closedSubscriberGroupInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.csgIndication = hwBlob.getBool(j);
        long j2 = j + 8;
        this.homeNodebName = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.csgIdentity = hwBlob.getInt32(j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ClosedSubscriberGroupInfo> arrayList) {
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
        hwBlob.putBool(j, this.csgIndication);
        hwBlob.putString(8 + j, this.homeNodebName);
        hwBlob.putInt32(j + 24, this.csgIdentity);
    }
}
