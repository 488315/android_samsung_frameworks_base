package android.hardware.contexthub.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class PhysicalSensor {
    public int sensorType = 0;
    public String type = new String();
    public String name = new String();

    /* renamed from: vendor, reason: collision with root package name */
    public String f3vendor = new String();
    public int version = 0;
    public int fifoReservedCount = 0;
    public int fifoMaxCount = 0;
    public long minDelayMs = 0;
    public long maxDelayMs = 0;
    public float peakPowerMw = 0.0f;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PhysicalSensor.class) {
            return false;
        }
        PhysicalSensor physicalSensor = (PhysicalSensor) obj;
        return this.sensorType == physicalSensor.sensorType && HidlSupport.deepEquals(this.type, physicalSensor.type) && HidlSupport.deepEquals(this.name, physicalSensor.name) && HidlSupport.deepEquals(this.f3vendor, physicalSensor.f3vendor) && this.version == physicalSensor.version && this.fifoReservedCount == physicalSensor.fifoReservedCount && this.fifoMaxCount == physicalSensor.fifoMaxCount && this.minDelayMs == physicalSensor.minDelayMs && this.maxDelayMs == physicalSensor.maxDelayMs && this.peakPowerMw == physicalSensor.peakPowerMw;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sensorType))), Integer.valueOf(HidlSupport.deepHashCode(this.type)), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(this.f3vendor)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.version))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.fifoReservedCount))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.fifoMaxCount))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.minDelayMs))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.maxDelayMs))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.peakPowerMw))));
    }

    public final String toString() {
        return "{.sensorType = " + SensorType.toString(this.sensorType) + ", .type = " + this.type + ", .name = " + this.name + ", .vendor = " + this.f3vendor + ", .version = " + this.version + ", .fifoReservedCount = " + this.fifoReservedCount + ", .fifoMaxCount = " + this.fifoMaxCount + ", .minDelayMs = " + this.minDelayMs + ", .maxDelayMs = " + this.maxDelayMs + ", .peakPowerMw = " + this.peakPowerMw + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final ArrayList<PhysicalSensor> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhysicalSensor> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            PhysicalSensor physicalSensor = new PhysicalSensor();
            physicalSensor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(physicalSensor);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.sensorType = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.type = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 24;
        this.name = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        long j4 = j + 40;
        this.f3vendor = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j4, false);
        this.version = hwBlob.getInt32(j + 56);
        this.fifoReservedCount = hwBlob.getInt32(j + 60);
        this.fifoMaxCount = hwBlob.getInt32(j + 64);
        this.minDelayMs = hwBlob.getInt64(j + 72);
        this.maxDelayMs = hwBlob.getInt64(j + 80);
        this.peakPowerMw = hwBlob.getFloat(j + 88);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhysicalSensor> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.sensorType);
        hwBlob.putString(8 + j, this.type);
        hwBlob.putString(24 + j, this.name);
        hwBlob.putString(40 + j, this.f3vendor);
        hwBlob.putInt32(56 + j, this.version);
        hwBlob.putInt32(60 + j, this.fifoReservedCount);
        hwBlob.putInt32(64 + j, this.fifoMaxCount);
        hwBlob.putInt64(72 + j, this.minDelayMs);
        hwBlob.putInt64(80 + j, this.maxDelayMs);
        hwBlob.putFloat(j + 88, this.peakPowerMw);
    }
}
