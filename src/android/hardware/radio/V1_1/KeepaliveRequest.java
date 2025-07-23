package android.hardware.radio.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class KeepaliveRequest {
    public int type = 0;
    public ArrayList<Byte> sourceAddress = new ArrayList<>();
    public int sourcePort = 0;
    public ArrayList<Byte> destinationAddress = new ArrayList<>();
    public int destinationPort = 0;
    public int maxKeepaliveIntervalMillis = 0;
    public int cid = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != KeepaliveRequest.class) {
            return false;
        }
        KeepaliveRequest keepaliveRequest = (KeepaliveRequest) obj;
        return this.type == keepaliveRequest.type && HidlSupport.deepEquals(this.sourceAddress, keepaliveRequest.sourceAddress) && this.sourcePort == keepaliveRequest.sourcePort && HidlSupport.deepEquals(this.destinationAddress, keepaliveRequest.destinationAddress) && this.destinationPort == keepaliveRequest.destinationPort && this.maxKeepaliveIntervalMillis == keepaliveRequest.maxKeepaliveIntervalMillis && this.cid == keepaliveRequest.cid;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.sourceAddress)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sourcePort))), Integer.valueOf(HidlSupport.deepHashCode(this.destinationAddress)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.destinationPort))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxKeepaliveIntervalMillis))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cid))));
    }

    public final String toString() {
        return "{.type = " + KeepaliveType.toString(this.type) + ", .sourceAddress = " + this.sourceAddress + ", .sourcePort = " + this.sourcePort + ", .destinationAddress = " + this.destinationAddress + ", .destinationPort = " + this.destinationPort + ", .maxKeepaliveIntervalMillis = " + this.maxKeepaliveIntervalMillis + ", .cid = " + this.cid + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final ArrayList<KeepaliveRequest> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<KeepaliveRequest> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            KeepaliveRequest keepaliveRequest = new KeepaliveRequest();
            keepaliveRequest.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(keepaliveRequest);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        int int32 = hwBlob.getInt32(16 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j + 8, true);
        this.sourceAddress.clear();
        for (int i = 0; i < int32; i++) {
            this.sourceAddress.add(Byte.valueOf(readEmbeddedBuffer.getInt8(i)));
        }
        this.sourcePort = hwBlob.getInt32(24 + j);
        int int322 = hwBlob.getInt32(40 + j);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322, hwBlob.handle(), j + 32, true);
        this.destinationAddress.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            this.destinationAddress.add(Byte.valueOf(readEmbeddedBuffer2.getInt8(i2)));
        }
        this.destinationPort = hwBlob.getInt32(48 + j);
        this.maxKeepaliveIntervalMillis = hwBlob.getInt32(52 + j);
        this.cid = hwBlob.getInt32(j + 56);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<KeepaliveRequest> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 64);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.type);
        int size = this.sourceAddress.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(20 + j, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.sourceAddress.get(i).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putInt32(24 + j, this.sourcePort);
        int size2 = this.destinationAddress.size();
        long j3 = 32 + j;
        hwBlob.putInt32(40 + j, size2);
        hwBlob.putBool(44 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putInt8(i2, this.destinationAddress.get(i2).byteValue());
        }
        hwBlob.putBlob(j3, hwBlob3);
        hwBlob.putInt32(48 + j, this.destinationPort);
        hwBlob.putInt32(52 + j, this.maxKeepaliveIntervalMillis);
        hwBlob.putInt32(j + 56, this.cid);
    }
}
