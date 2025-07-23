package android.hardware.contexthub.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ContextHub {
    public String name = new String();

    /* renamed from: vendor, reason: collision with root package name */
    public String f2vendor = new String();
    public String toolchain = new String();
    public int platformVersion = 0;
    public int toolchainVersion = 0;
    public int hubId = 0;
    public float peakMips = 0.0f;
    public float stoppedPowerDrawMw = 0.0f;
    public float sleepPowerDrawMw = 0.0f;
    public float peakPowerDrawMw = 0.0f;
    public ArrayList<PhysicalSensor> connectedSensors = new ArrayList<>();
    public int maxSupportedMsgLen = 0;
    public long chrePlatformId = 0;
    public byte chreApiMajorVersion = 0;
    public byte chreApiMinorVersion = 0;
    public short chrePatchVersion = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ContextHub.class) {
            return false;
        }
        ContextHub contextHub = (ContextHub) obj;
        return HidlSupport.deepEquals(this.name, contextHub.name) && HidlSupport.deepEquals(this.f2vendor, contextHub.f2vendor) && HidlSupport.deepEquals(this.toolchain, contextHub.toolchain) && this.platformVersion == contextHub.platformVersion && this.toolchainVersion == contextHub.toolchainVersion && this.hubId == contextHub.hubId && this.peakMips == contextHub.peakMips && this.stoppedPowerDrawMw == contextHub.stoppedPowerDrawMw && this.sleepPowerDrawMw == contextHub.sleepPowerDrawMw && this.peakPowerDrawMw == contextHub.peakPowerDrawMw && HidlSupport.deepEquals(this.connectedSensors, contextHub.connectedSensors) && this.maxSupportedMsgLen == contextHub.maxSupportedMsgLen && this.chrePlatformId == contextHub.chrePlatformId && this.chreApiMajorVersion == contextHub.chreApiMajorVersion && this.chreApiMinorVersion == contextHub.chreApiMinorVersion && this.chrePatchVersion == contextHub.chrePatchVersion;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(this.f2vendor)), Integer.valueOf(HidlSupport.deepHashCode(this.toolchain)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.platformVersion))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.toolchainVersion))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.hubId))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.peakMips))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.stoppedPowerDrawMw))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.sleepPowerDrawMw))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.peakPowerDrawMw))), Integer.valueOf(HidlSupport.deepHashCode(this.connectedSensors)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxSupportedMsgLen))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.chrePlatformId))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.chreApiMajorVersion))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.chreApiMinorVersion))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.chrePatchVersion))));
    }

    public final String toString() {
        return "{.name = " + this.name + ", .vendor = " + this.f2vendor + ", .toolchain = " + this.toolchain + ", .platformVersion = " + this.platformVersion + ", .toolchainVersion = " + this.toolchainVersion + ", .hubId = " + this.hubId + ", .peakMips = " + this.peakMips + ", .stoppedPowerDrawMw = " + this.stoppedPowerDrawMw + ", .sleepPowerDrawMw = " + this.sleepPowerDrawMw + ", .peakPowerDrawMw = " + this.peakPowerDrawMw + ", .connectedSensors = " + this.connectedSensors + ", .maxSupportedMsgLen = " + this.maxSupportedMsgLen + ", .chrePlatformId = " + this.chrePlatformId + ", .chreApiMajorVersion = " + ((int) this.chreApiMajorVersion) + ", .chreApiMinorVersion = " + ((int) this.chreApiMinorVersion) + ", .chrePatchVersion = " + ((int) this.chrePatchVersion) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final ArrayList<ContextHub> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ContextHub> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ContextHub contextHub = new ContextHub();
            contextHub.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            arrayList.add(contextHub);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.name = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.f2vendor = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 32;
        this.toolchain = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        this.platformVersion = hwBlob.getInt32(j + 48);
        this.toolchainVersion = hwBlob.getInt32(j + 52);
        this.hubId = hwBlob.getInt32(j + 56);
        this.peakMips = hwBlob.getFloat(j + 60);
        this.stoppedPowerDrawMw = hwBlob.getFloat(j + 64);
        this.sleepPowerDrawMw = hwBlob.getFloat(j + 68);
        this.peakPowerDrawMw = hwBlob.getFloat(j + 72);
        int int32 = hwBlob.getInt32(j + 88);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, hwBlob.handle(), j + 80, true);
        this.connectedSensors.clear();
        for (int i = 0; i < int32; i++) {
            PhysicalSensor physicalSensor = new PhysicalSensor();
            physicalSensor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            this.connectedSensors.add(physicalSensor);
        }
        this.maxSupportedMsgLen = hwBlob.getInt32(j + 96);
        this.chrePlatformId = hwBlob.getInt64(j + 104);
        this.chreApiMajorVersion = hwBlob.getInt8(j + 112);
        this.chreApiMinorVersion = hwBlob.getInt8(j + 113);
        this.chrePatchVersion = hwBlob.getInt16(j + 114);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ContextHub> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(j, this.name);
        hwBlob.putString(16 + j, this.f2vendor);
        hwBlob.putString(32 + j, this.toolchain);
        hwBlob.putInt32(48 + j, this.platformVersion);
        hwBlob.putInt32(52 + j, this.toolchainVersion);
        hwBlob.putInt32(56 + j, this.hubId);
        hwBlob.putFloat(60 + j, this.peakMips);
        hwBlob.putFloat(64 + j, this.stoppedPowerDrawMw);
        hwBlob.putFloat(68 + j, this.sleepPowerDrawMw);
        hwBlob.putFloat(72 + j, this.peakPowerDrawMw);
        int size = this.connectedSensors.size();
        long j2 = 80 + j;
        hwBlob.putInt32(88 + j, size);
        hwBlob.putBool(92 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            this.connectedSensors.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putInt32(96 + j, this.maxSupportedMsgLen);
        hwBlob.putInt64(104 + j, this.chrePlatformId);
        hwBlob.putInt8(112 + j, this.chreApiMajorVersion);
        hwBlob.putInt8(113 + j, this.chreApiMinorVersion);
        hwBlob.putInt16(j + 114, this.chrePatchVersion);
    }
}
