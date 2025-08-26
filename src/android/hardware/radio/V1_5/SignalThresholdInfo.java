package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SignalThresholdInfo {
    public int signalMeasurement = 0;
    public int hysteresisMs = 0;
    public int hysteresisDb = 0;
    public ArrayList<Integer> thresholds = new ArrayList<>();
    public boolean isEnabled = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SignalThresholdInfo.class) {
            return false;
        }
        SignalThresholdInfo signalThresholdInfo = (SignalThresholdInfo) obj;
        return this.signalMeasurement == signalThresholdInfo.signalMeasurement && this.hysteresisMs == signalThresholdInfo.hysteresisMs && this.hysteresisDb == signalThresholdInfo.hysteresisDb && HidlSupport.deepEquals(this.thresholds, signalThresholdInfo.thresholds) && this.isEnabled == signalThresholdInfo.isEnabled;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalMeasurement))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.hysteresisMs))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.hysteresisDb))), Integer.valueOf(HidlSupport.deepHashCode(this.thresholds)), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isEnabled))));
    }

    public final String toString() {
        return "{.signalMeasurement = " + SignalMeasurementType.toString(this.signalMeasurement) + ", .hysteresisMs = " + this.hysteresisMs + ", .hysteresisDb = " + this.hysteresisDb + ", .thresholds = " + this.thresholds + ", .isEnabled = " + this.isEnabled + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<SignalThresholdInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SignalThresholdInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SignalThresholdInfo signalThresholdInfo = new SignalThresholdInfo();
            signalThresholdInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(signalThresholdInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.signalMeasurement = hwBlob.getInt32(j);
        this.hysteresisMs = hwBlob.getInt32(4 + j);
        this.hysteresisDb = hwBlob.getInt32(8 + j);
        int int32 = hwBlob.getInt32(24 + j);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j + 16, true);
        this.thresholds.clear();
        for (int i = 0; i < int32; i++) {
            this.thresholds.add(Integer.valueOf(embeddedBuffer.getInt32(i * 4)));
        }
        this.isEnabled = hwBlob.getBool(j + 32);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SignalThresholdInfo> arrayList) {
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
        hwBlob.putInt32(j, this.signalMeasurement);
        hwBlob.putInt32(4 + j, this.hysteresisMs);
        hwBlob.putInt32(8 + j, this.hysteresisDb);
        int size = this.thresholds.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size);
        hwBlob.putBool(28 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.thresholds.get(i).intValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putBool(j + 32, this.isEnabled);
    }
}
