package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AppStatus {
    public android.hardware.radio.V1_0.AppStatus base = new android.hardware.radio.V1_0.AppStatus();
    public int persoSubstate = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != AppStatus.class) {
            return false;
        }
        AppStatus appStatus = (AppStatus) obj;
        return HidlSupport.deepEquals(this.base, appStatus.base) && this.persoSubstate == appStatus.persoSubstate;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.persoSubstate))));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .persoSubstate = " + PersoSubstate.toString(this.persoSubstate) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final ArrayList<AppStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<AppStatus> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            AppStatus appStatus = new AppStatus();
            appStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(appStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.persoSubstate = hwBlob.getInt32(j + 64);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<AppStatus> arrayList) {
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
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(j + 64, this.persoSubstate);
    }
}
