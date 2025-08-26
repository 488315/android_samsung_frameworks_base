package android.hardware.gnss.measurement_corrections.V1_0;

import android.hardware.scontext.SContextConstants;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ReflectingPlane {
    public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double altitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double azimuthDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ReflectingPlane.class) {
            return false;
        }
        ReflectingPlane reflectingPlane = (ReflectingPlane) obj;
        return this.latitudeDegrees == reflectingPlane.latitudeDegrees && this.longitudeDegrees == reflectingPlane.longitudeDegrees && this.altitudeMeters == reflectingPlane.altitudeMeters && this.azimuthDegrees == reflectingPlane.azimuthDegrees;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.latitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.longitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.altitudeMeters))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.azimuthDegrees))));
    }

    public final String toString() {
        return "{.latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .azimuthDegrees = " + this.azimuthDegrees + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<ReflectingPlane> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ReflectingPlane> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ReflectingPlane reflectingPlane = new ReflectingPlane();
            reflectingPlane.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(reflectingPlane);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.latitudeDegrees = hwBlob.getDouble(j);
        this.longitudeDegrees = hwBlob.getDouble(8 + j);
        this.altitudeMeters = hwBlob.getDouble(16 + j);
        this.azimuthDegrees = hwBlob.getDouble(j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ReflectingPlane> arrayList) {
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
        hwBlob.putDouble(j, this.latitudeDegrees);
        hwBlob.putDouble(8 + j, this.longitudeDegrees);
        hwBlob.putDouble(16 + j, this.altitudeMeters);
        hwBlob.putDouble(j + 24, this.azimuthDegrees);
    }
}
