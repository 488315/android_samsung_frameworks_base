package android.hardware.thermal.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Temperature {
    public int type = 0;
    public String name = new String();
    public float value = 0.0f;
    public int throttlingStatus = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Temperature.class) {
            return false;
        }
        Temperature temperature = (Temperature) obj;
        return this.type == temperature.type && HidlSupport.deepEquals(this.name, temperature.name) && this.value == temperature.value && this.throttlingStatus == temperature.throttlingStatus;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.value))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.throttlingStatus))));
    }

    public final String toString() {
        return "{.type = " + TemperatureType.toString(this.type) + ", .name = " + this.name + ", .value = " + this.value + ", .throttlingStatus = " + ThrottlingSeverity.toString(this.throttlingStatus) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<Temperature> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Temperature> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            Temperature temperature = new Temperature();
            temperature.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(temperature);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.value = hwBlob.getFloat(24 + j);
        this.throttlingStatus = hwBlob.getInt32(j + 28);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Temperature> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.type);
        hwBlob.putString(8 + j, this.name);
        hwBlob.putFloat(24 + j, this.value);
        hwBlob.putInt32(j + 28, this.throttlingStatus);
    }
}
