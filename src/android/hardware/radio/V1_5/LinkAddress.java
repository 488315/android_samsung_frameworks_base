package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LinkAddress {
    public String address = new String();
    public long deprecationTime = 0;
    public long expirationTime = 0;
    public int properties;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != LinkAddress.class) {
            return false;
        }
        LinkAddress linkAddress = (LinkAddress) obj;
        return HidlSupport.deepEquals(this.address, linkAddress.address) && HidlSupport.deepEquals(Integer.valueOf(this.properties), Integer.valueOf(linkAddress.properties)) && this.deprecationTime == linkAddress.deprecationTime && this.expirationTime == linkAddress.expirationTime;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.address)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.properties))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.deprecationTime))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.expirationTime))));
    }

    public final String toString() {
        return "{.address = " + this.address + ", .properties = " + AddressProperty.dumpBitfield(this.properties) + ", .deprecationTime = " + this.deprecationTime + ", .expirationTime = " + this.expirationTime + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<LinkAddress> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LinkAddress> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            LinkAddress linkAddress = new LinkAddress();
            linkAddress.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(linkAddress);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.address = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j, false);
        this.properties = hwBlob.getInt32(16 + j);
        this.deprecationTime = hwBlob.getInt64(24 + j);
        this.expirationTime = hwBlob.getInt64(32 + j);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LinkAddress> arrayList) {
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
        hwBlob.putString(j, this.address);
        hwBlob.putInt32(16 + j, this.properties);
        hwBlob.putInt64(24 + j, this.deprecationTime);
        hwBlob.putInt64(j + 32, this.expirationTime);
    }
}
