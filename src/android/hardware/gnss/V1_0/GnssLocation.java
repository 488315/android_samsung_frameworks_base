package android.hardware.gnss.V1_0;

import android.hardware.scontext.SContextConstants;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GnssLocation {
    public short gnssLocationFlags;
    public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double altitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public float speedMetersPerSec = 0.0f;
    public float bearingDegrees = 0.0f;
    public float horizontalAccuracyMeters = 0.0f;
    public float verticalAccuracyMeters = 0.0f;
    public float speedAccuracyMetersPerSecond = 0.0f;
    public float bearingAccuracyDegrees = 0.0f;
    public long timestamp = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != GnssLocation.class) {
            return false;
        }
        GnssLocation gnssLocation = (GnssLocation) obj;
        return HidlSupport.deepEquals(Short.valueOf(this.gnssLocationFlags), Short.valueOf(gnssLocation.gnssLocationFlags)) && this.latitudeDegrees == gnssLocation.latitudeDegrees && this.longitudeDegrees == gnssLocation.longitudeDegrees && this.altitudeMeters == gnssLocation.altitudeMeters && this.speedMetersPerSec == gnssLocation.speedMetersPerSec && this.bearingDegrees == gnssLocation.bearingDegrees && this.horizontalAccuracyMeters == gnssLocation.horizontalAccuracyMeters && this.verticalAccuracyMeters == gnssLocation.verticalAccuracyMeters && this.speedAccuracyMetersPerSecond == gnssLocation.speedAccuracyMetersPerSecond && this.bearingAccuracyDegrees == gnssLocation.bearingAccuracyDegrees && this.timestamp == gnssLocation.timestamp;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.gnssLocationFlags))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.latitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.longitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.altitudeMeters))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.speedMetersPerSec))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.bearingDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.horizontalAccuracyMeters))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.verticalAccuracyMeters))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.speedAccuracyMetersPerSecond))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.bearingAccuracyDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.timestamp))));
    }

    public final String toString() {
        return "{.gnssLocationFlags = " + GnssLocationFlags.dumpBitfield(this.gnssLocationFlags) + ", .latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .speedMetersPerSec = " + this.speedMetersPerSec + ", .bearingDegrees = " + this.bearingDegrees + ", .horizontalAccuracyMeters = " + this.horizontalAccuracyMeters + ", .verticalAccuracyMeters = " + this.verticalAccuracyMeters + ", .speedAccuracyMetersPerSecond = " + this.speedAccuracyMetersPerSecond + ", .bearingAccuracyDegrees = " + this.bearingAccuracyDegrees + ", .timestamp = " + this.timestamp + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final ArrayList<GnssLocation> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GnssLocation> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            GnssLocation gnssLocation = new GnssLocation();
            gnssLocation.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(gnssLocation);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.gnssLocationFlags = hwBlob.getInt16(j);
        this.latitudeDegrees = hwBlob.getDouble(8 + j);
        this.longitudeDegrees = hwBlob.getDouble(16 + j);
        this.altitudeMeters = hwBlob.getDouble(24 + j);
        this.speedMetersPerSec = hwBlob.getFloat(32 + j);
        this.bearingDegrees = hwBlob.getFloat(36 + j);
        this.horizontalAccuracyMeters = hwBlob.getFloat(40 + j);
        this.verticalAccuracyMeters = hwBlob.getFloat(44 + j);
        this.speedAccuracyMetersPerSecond = hwBlob.getFloat(48 + j);
        this.bearingAccuracyDegrees = hwBlob.getFloat(52 + j);
        this.timestamp = hwBlob.getInt64(j + 56);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssLocation> arrayList) {
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
        hwBlob.putInt16(j, this.gnssLocationFlags);
        hwBlob.putDouble(8 + j, this.latitudeDegrees);
        hwBlob.putDouble(16 + j, this.longitudeDegrees);
        hwBlob.putDouble(24 + j, this.altitudeMeters);
        hwBlob.putFloat(32 + j, this.speedMetersPerSec);
        hwBlob.putFloat(36 + j, this.bearingDegrees);
        hwBlob.putFloat(40 + j, this.horizontalAccuracyMeters);
        hwBlob.putFloat(44 + j, this.verticalAccuracyMeters);
        hwBlob.putFloat(48 + j, this.speedAccuracyMetersPerSecond);
        hwBlob.putFloat(52 + j, this.bearingAccuracyDegrees);
        hwBlob.putInt64(j + 56, this.timestamp);
    }
}
