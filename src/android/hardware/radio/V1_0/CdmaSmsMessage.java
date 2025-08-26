package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaSmsMessage {
    public int teleserviceId = 0;
    public boolean isServicePresent = false;
    public int serviceCategory = 0;
    public CdmaSmsAddress address = new CdmaSmsAddress();
    public CdmaSmsSubaddress subAddress = new CdmaSmsSubaddress();
    public ArrayList<Byte> bearerData = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaSmsMessage.class) {
            return false;
        }
        CdmaSmsMessage cdmaSmsMessage = (CdmaSmsMessage) obj;
        return this.teleserviceId == cdmaSmsMessage.teleserviceId && this.isServicePresent == cdmaSmsMessage.isServicePresent && this.serviceCategory == cdmaSmsMessage.serviceCategory && HidlSupport.deepEquals(this.address, cdmaSmsMessage.address) && HidlSupport.deepEquals(this.subAddress, cdmaSmsMessage.subAddress) && HidlSupport.deepEquals(this.bearerData, cdmaSmsMessage.bearerData);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.teleserviceId))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isServicePresent))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.serviceCategory))), Integer.valueOf(HidlSupport.deepHashCode(this.address)), Integer.valueOf(HidlSupport.deepHashCode(this.subAddress)), Integer.valueOf(HidlSupport.deepHashCode(this.bearerData)));
    }

    public final String toString() {
        return "{.teleserviceId = " + this.teleserviceId + ", .isServicePresent = " + this.isServicePresent + ", .serviceCategory = " + this.serviceCategory + ", .address = " + this.address + ", .subAddress = " + this.subAddress + ", .bearerData = " + this.bearerData + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<CdmaSmsMessage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsMessage> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
            cdmaSmsMessage.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            arrayList.add(cdmaSmsMessage);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.teleserviceId = hwBlob.getInt32(j);
        this.isServicePresent = hwBlob.getBool(4 + j);
        this.serviceCategory = hwBlob.getInt32(8 + j);
        this.address.readEmbeddedFromParcel(hwParcel, hwBlob, 16 + j);
        this.subAddress.readEmbeddedFromParcel(hwParcel, hwBlob, 48 + j);
        long j2 = j + 72;
        int int32 = hwBlob.getInt32(j + 80);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j2, true);
        this.bearerData.clear();
        for (int i = 0; i < int32; i++) {
            this.bearerData.add(Byte.valueOf(embeddedBuffer.getInt8(i)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsMessage> arrayList) {
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
        hwBlob.putInt32(j, this.teleserviceId);
        hwBlob.putBool(4 + j, this.isServicePresent);
        hwBlob.putInt32(8 + j, this.serviceCategory);
        this.address.writeEmbeddedToBlob(hwBlob, 16 + j);
        this.subAddress.writeEmbeddedToBlob(hwBlob, 48 + j);
        int size = this.bearerData.size();
        long j2 = 72 + j;
        hwBlob.putInt32(80 + j, size);
        hwBlob.putBool(j + 84, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.bearerData.get(i).byteValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
