package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AppStatus {
    public int appType = 0;
    public int appState = 0;
    public int persoSubstate = 0;
    public String aidPtr = new String();
    public String appLabelPtr = new String();
    public int pin1Replaced = 0;
    public int pin1 = 0;
    public int pin2 = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != AppStatus.class) {
            return false;
        }
        AppStatus appStatus = (AppStatus) obj;
        return this.appType == appStatus.appType && this.appState == appStatus.appState && this.persoSubstate == appStatus.persoSubstate && HidlSupport.deepEquals(this.aidPtr, appStatus.aidPtr) && HidlSupport.deepEquals(this.appLabelPtr, appStatus.appLabelPtr) && this.pin1Replaced == appStatus.pin1Replaced && this.pin1 == appStatus.pin1 && this.pin2 == appStatus.pin2;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.appType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.appState))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.persoSubstate))), Integer.valueOf(HidlSupport.deepHashCode(this.aidPtr)), Integer.valueOf(HidlSupport.deepHashCode(this.appLabelPtr)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pin1Replaced))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pin1))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pin2))));
    }

    public final String toString() {
        return "{.appType = " + AppType.toString(this.appType) + ", .appState = " + AppState.toString(this.appState) + ", .persoSubstate = " + PersoSubstate.toString(this.persoSubstate) + ", .aidPtr = " + this.aidPtr + ", .appLabelPtr = " + this.appLabelPtr + ", .pin1Replaced = " + this.pin1Replaced + ", .pin1 = " + PinState.toString(this.pin1) + ", .pin2 = " + PinState.toString(this.pin2) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final ArrayList<AppStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<AppStatus> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            AppStatus appStatus = new AppStatus();
            appStatus.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 64);
            arrayList.add(appStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.appType = hwBlob.getInt32(j);
        this.appState = hwBlob.getInt32(j + 4);
        this.persoSubstate = hwBlob.getInt32(j + 8);
        long j2 = j + 16;
        this.aidPtr = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 32;
        this.appLabelPtr = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        this.pin1Replaced = hwBlob.getInt32(j + 48);
        this.pin1 = hwBlob.getInt32(j + 52);
        this.pin2 = hwBlob.getInt32(j + 56);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<AppStatus> arrayList) {
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
        hwBlob.putInt32(j, this.appType);
        hwBlob.putInt32(4 + j, this.appState);
        hwBlob.putInt32(8 + j, this.persoSubstate);
        hwBlob.putString(16 + j, this.aidPtr);
        hwBlob.putString(32 + j, this.appLabelPtr);
        hwBlob.putInt32(48 + j, this.pin1Replaced);
        hwBlob.putInt32(52 + j, this.pin1);
        hwBlob.putInt32(j + 56, this.pin2);
    }
}
