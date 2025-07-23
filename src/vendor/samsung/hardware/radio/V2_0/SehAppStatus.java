package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.AppStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehAppStatus {
    public AppStatus base = new AppStatus();
    public int pin1NumRetries = 0;
    public int puk1NumRetries = 0;
    public int pin2NumRetries = 0;
    public int puk2NumRetries = 0;
    public int persoUnblockRetries = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehAppStatus.class) {
            return false;
        }
        SehAppStatus sehAppStatus = (SehAppStatus) obj;
        return HidlSupport.deepEquals(this.base, sehAppStatus.base) && this.pin1NumRetries == sehAppStatus.pin1NumRetries && this.puk1NumRetries == sehAppStatus.puk1NumRetries && this.pin2NumRetries == sehAppStatus.pin2NumRetries && this.puk2NumRetries == sehAppStatus.puk2NumRetries && this.persoUnblockRetries == sehAppStatus.persoUnblockRetries;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pin1NumRetries))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.puk1NumRetries))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pin2NumRetries))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.puk2NumRetries))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.persoUnblockRetries))));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .pin1NumRetries = " + this.pin1NumRetries + ", .puk1NumRetries = " + this.puk1NumRetries + ", .pin2NumRetries = " + this.pin2NumRetries + ", .puk2NumRetries = " + this.puk2NumRetries + ", .persoUnblockRetries = " + this.persoUnblockRetries + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<SehAppStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehAppStatus> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehAppStatus sehAppStatus = new SehAppStatus();
            sehAppStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(sehAppStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.pin1NumRetries = hwBlob.getInt32(64 + j);
        this.puk1NumRetries = hwBlob.getInt32(68 + j);
        this.pin2NumRetries = hwBlob.getInt32(72 + j);
        this.puk2NumRetries = hwBlob.getInt32(76 + j);
        this.persoUnblockRetries = hwBlob.getInt32(j + 80);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehAppStatus> arrayList) {
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
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(64 + j, this.pin1NumRetries);
        hwBlob.putInt32(68 + j, this.puk1NumRetries);
        hwBlob.putInt32(72 + j, this.pin2NumRetries);
        hwBlob.putInt32(76 + j, this.puk2NumRetries);
        hwBlob.putInt32(j + 80, this.persoUnblockRetries);
    }
}
