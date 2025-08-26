package android.hardware.thermal.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class TemperatureThreshold {
    public int type = 0;
    public String name = new String();
    public float[] hotThrottlingThresholds = new float[7];
    public float[] coldThrottlingThresholds = new float[7];
    public float vrThrottlingThreshold = 0.0f;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != TemperatureThreshold.class) {
            return false;
        }
        TemperatureThreshold temperatureThreshold = (TemperatureThreshold) obj;
        return this.type == temperatureThreshold.type && HidlSupport.deepEquals(this.name, temperatureThreshold.name) && HidlSupport.deepEquals(this.hotThrottlingThresholds, temperatureThreshold.hotThrottlingThresholds) && HidlSupport.deepEquals(this.coldThrottlingThresholds, temperatureThreshold.coldThrottlingThresholds) && this.vrThrottlingThreshold == temperatureThreshold.vrThrottlingThreshold;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(this.hotThrottlingThresholds)), Integer.valueOf(HidlSupport.deepHashCode(this.coldThrottlingThresholds)), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.vrThrottlingThreshold))));
    }

    public final String toString() {
        return "{.type = " + TemperatureType.toString(this.type) + ", .name = " + this.name + ", .hotThrottlingThresholds = " + Arrays.toString(this.hotThrottlingThresholds) + ", .coldThrottlingThresholds = " + Arrays.toString(this.coldThrottlingThresholds) + ", .vrThrottlingThreshold = " + this.vrThrottlingThreshold + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final ArrayList<TemperatureThreshold> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<TemperatureThreshold> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            TemperatureThreshold temperatureThreshold = new TemperatureThreshold();
            temperatureThreshold.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            arrayList.add(temperatureThreshold);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        hwBlob.copyToFloatArray(24 + j, this.hotThrottlingThresholds, 7);
        hwBlob.copyToFloatArray(52 + j, this.coldThrottlingThresholds, 7);
        this.vrThrottlingThreshold = hwBlob.getFloat(j + 80);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<TemperatureThreshold> arrayList) {
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
        hwBlob.putInt32(j, this.type);
        hwBlob.putString(8 + j, this.name);
        long j2 = 24 + j;
        float[] fArr = this.hotThrottlingThresholds;
        if (fArr == null || fArr.length != 7) {
            throw new IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putFloatArray(j2, fArr);
        long j3 = 52 + j;
        float[] fArr2 = this.coldThrottlingThresholds;
        if (fArr2 == null || fArr2.length != 7) {
            throw new IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putFloatArray(j3, fArr2);
        hwBlob.putFloat(j + 80, this.vrThrottlingThreshold);
    }
}
