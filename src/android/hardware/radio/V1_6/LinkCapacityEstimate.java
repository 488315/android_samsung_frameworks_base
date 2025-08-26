package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LinkCapacityEstimate {
    public int downlinkCapacityKbps = 0;
    public int uplinkCapacityKbps = 0;
    public int secondaryDownlinkCapacityKbps = 0;
    public int secondaryUplinkCapacityKbps = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != LinkCapacityEstimate.class) {
            return false;
        }
        LinkCapacityEstimate linkCapacityEstimate = (LinkCapacityEstimate) obj;
        return this.downlinkCapacityKbps == linkCapacityEstimate.downlinkCapacityKbps && this.uplinkCapacityKbps == linkCapacityEstimate.uplinkCapacityKbps && this.secondaryDownlinkCapacityKbps == linkCapacityEstimate.secondaryDownlinkCapacityKbps && this.secondaryUplinkCapacityKbps == linkCapacityEstimate.secondaryUplinkCapacityKbps;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.downlinkCapacityKbps))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.uplinkCapacityKbps))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.secondaryDownlinkCapacityKbps))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.secondaryUplinkCapacityKbps))));
    }

    public final String toString() {
        return "{.downlinkCapacityKbps = " + this.downlinkCapacityKbps + ", .uplinkCapacityKbps = " + this.uplinkCapacityKbps + ", .secondaryDownlinkCapacityKbps = " + this.secondaryDownlinkCapacityKbps + ", .secondaryUplinkCapacityKbps = " + this.secondaryUplinkCapacityKbps + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<LinkCapacityEstimate> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LinkCapacityEstimate> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            LinkCapacityEstimate linkCapacityEstimate = new LinkCapacityEstimate();
            linkCapacityEstimate.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 16);
            arrayList.add(linkCapacityEstimate);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.downlinkCapacityKbps = hwBlob.getInt32(j);
        this.uplinkCapacityKbps = hwBlob.getInt32(4 + j);
        this.secondaryDownlinkCapacityKbps = hwBlob.getInt32(8 + j);
        this.secondaryUplinkCapacityKbps = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LinkCapacityEstimate> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.downlinkCapacityKbps);
        hwBlob.putInt32(4 + j, this.uplinkCapacityKbps);
        hwBlob.putInt32(8 + j, this.secondaryDownlinkCapacityKbps);
        hwBlob.putInt32(j + 12, this.secondaryUplinkCapacityKbps);
    }
}
