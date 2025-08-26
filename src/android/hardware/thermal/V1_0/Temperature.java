package android.hardware.thermal.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Temperature {
    public int type = 0;
    public String name = new String();
    public float currentValue = 0.0f;
    public float throttlingThreshold = 0.0f;
    public float shutdownThreshold = 0.0f;
    public float vrThrottlingThreshold = 0.0f;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Temperature.class) {
            return false;
        }
        Temperature temperature = (Temperature) obj;
        return this.type == temperature.type && HidlSupport.deepEquals(this.name, temperature.name) && this.currentValue == temperature.currentValue && this.throttlingThreshold == temperature.throttlingThreshold && this.shutdownThreshold == temperature.shutdownThreshold && this.vrThrottlingThreshold == temperature.vrThrottlingThreshold;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.currentValue))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.throttlingThreshold))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.shutdownThreshold))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.vrThrottlingThreshold))));
    }

    public final String toString() {
        return "{.type = " + TemperatureType.toString(this.type) + ", .name = " + this.name + ", .currentValue = " + this.currentValue + ", .throttlingThreshold = " + this.throttlingThreshold + ", .shutdownThreshold = " + this.shutdownThreshold + ", .vrThrottlingThreshold = " + this.vrThrottlingThreshold + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<Temperature> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Temperature> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            Temperature temperature = new Temperature();
            temperature.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(temperature);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.currentValue = hwBlob.getFloat(24 + j);
        this.throttlingThreshold = hwBlob.getFloat(28 + j);
        this.shutdownThreshold = hwBlob.getFloat(32 + j);
        this.vrThrottlingThreshold = hwBlob.getFloat(j + 36);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Temperature> arrayList) {
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
        hwBlob.putInt32(j, this.type);
        hwBlob.putString(8 + j, this.name);
        hwBlob.putFloat(24 + j, this.currentValue);
        hwBlob.putFloat(28 + j, this.throttlingThreshold);
        hwBlob.putFloat(32 + j, this.shutdownThreshold);
        hwBlob.putFloat(j + 36, this.vrThrottlingThreshold);
    }
}
