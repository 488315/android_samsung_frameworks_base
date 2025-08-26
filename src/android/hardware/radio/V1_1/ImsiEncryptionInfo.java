package android.hardware.radio.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ImsiEncryptionInfo {
    public String mcc = new String();
    public String mnc = new String();
    public ArrayList<Byte> carrierKey = new ArrayList<>();
    public String keyIdentifier = new String();
    public long expirationTime = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ImsiEncryptionInfo.class) {
            return false;
        }
        ImsiEncryptionInfo imsiEncryptionInfo = (ImsiEncryptionInfo) obj;
        return HidlSupport.deepEquals(this.mcc, imsiEncryptionInfo.mcc) && HidlSupport.deepEquals(this.mnc, imsiEncryptionInfo.mnc) && HidlSupport.deepEquals(this.carrierKey, imsiEncryptionInfo.carrierKey) && HidlSupport.deepEquals(this.keyIdentifier, imsiEncryptionInfo.keyIdentifier) && this.expirationTime == imsiEncryptionInfo.expirationTime;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.mcc)), Integer.valueOf(HidlSupport.deepHashCode(this.mnc)), Integer.valueOf(HidlSupport.deepHashCode(this.carrierKey)), Integer.valueOf(HidlSupport.deepHashCode(this.keyIdentifier)), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.expirationTime))));
    }

    public final String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .carrierKey = " + this.carrierKey + ", .keyIdentifier = " + this.keyIdentifier + ", .expirationTime = " + this.expirationTime + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final ArrayList<ImsiEncryptionInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ImsiEncryptionInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ImsiEncryptionInfo imsiEncryptionInfo = new ImsiEncryptionInfo();
            imsiEncryptionInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 72);
            arrayList.add(imsiEncryptionInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.mcc = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.mnc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        int int32 = hwBlob.getInt32(j + 40);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j + 32, true);
        this.carrierKey.clear();
        for (int i = 0; i < int32; i++) {
            this.carrierKey.add(Byte.valueOf(embeddedBuffer.getInt8(i)));
        }
        long j3 = j + 48;
        this.keyIdentifier = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        this.expirationTime = hwBlob.getInt64(j + 64);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ImsiEncryptionInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(j, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        int size = this.carrierKey.size();
        long j2 = 32 + j;
        hwBlob.putInt32(40 + j, size);
        hwBlob.putBool(44 + j, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.carrierKey.get(i).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putString(48 + j, this.keyIdentifier);
        hwBlob.putInt64(j + 64, this.expirationTime);
    }
}
