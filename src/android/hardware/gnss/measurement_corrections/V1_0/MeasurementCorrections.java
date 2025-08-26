package android.hardware.gnss.measurement_corrections.V1_0;

import android.hardware.scontext.SContextConstants;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MeasurementCorrections {
    public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double altitudeMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double horizontalPositionUncertaintyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double verticalPositionUncertaintyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public long toaGpsNanosecondsOfWeek = 0;
    public ArrayList<SingleSatCorrection> satCorrections = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != MeasurementCorrections.class) {
            return false;
        }
        MeasurementCorrections measurementCorrections = (MeasurementCorrections) obj;
        return this.latitudeDegrees == measurementCorrections.latitudeDegrees && this.longitudeDegrees == measurementCorrections.longitudeDegrees && this.altitudeMeters == measurementCorrections.altitudeMeters && this.horizontalPositionUncertaintyMeters == measurementCorrections.horizontalPositionUncertaintyMeters && this.verticalPositionUncertaintyMeters == measurementCorrections.verticalPositionUncertaintyMeters && this.toaGpsNanosecondsOfWeek == measurementCorrections.toaGpsNanosecondsOfWeek && HidlSupport.deepEquals(this.satCorrections, measurementCorrections.satCorrections);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.latitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.longitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.altitudeMeters))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.horizontalPositionUncertaintyMeters))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.verticalPositionUncertaintyMeters))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.toaGpsNanosecondsOfWeek))), Integer.valueOf(HidlSupport.deepHashCode(this.satCorrections)));
    }

    public final String toString() {
        return "{.latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .horizontalPositionUncertaintyMeters = " + this.horizontalPositionUncertaintyMeters + ", .verticalPositionUncertaintyMeters = " + this.verticalPositionUncertaintyMeters + ", .toaGpsNanosecondsOfWeek = " + this.toaGpsNanosecondsOfWeek + ", .satCorrections = " + this.satCorrections + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final ArrayList<MeasurementCorrections> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<MeasurementCorrections> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            MeasurementCorrections measurementCorrections = new MeasurementCorrections();
            measurementCorrections.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 64);
            arrayList.add(measurementCorrections);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.latitudeDegrees = hwBlob.getDouble(j);
        this.longitudeDegrees = hwBlob.getDouble(8 + j);
        this.altitudeMeters = hwBlob.getDouble(16 + j);
        this.horizontalPositionUncertaintyMeters = hwBlob.getDouble(24 + j);
        this.verticalPositionUncertaintyMeters = hwBlob.getDouble(32 + j);
        this.toaGpsNanosecondsOfWeek = hwBlob.getInt64(40 + j);
        long j2 = j + 48;
        int int32 = hwBlob.getInt32(j + 56);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, hwBlob.handle(), j2, true);
        this.satCorrections.clear();
        for (int i = 0; i < int32; i++) {
            SingleSatCorrection singleSatCorrection = new SingleSatCorrection();
            singleSatCorrection.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 56);
            this.satCorrections.add(singleSatCorrection);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<MeasurementCorrections> arrayList) {
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
        hwBlob.putDouble(j, this.latitudeDegrees);
        hwBlob.putDouble(8 + j, this.longitudeDegrees);
        hwBlob.putDouble(16 + j, this.altitudeMeters);
        hwBlob.putDouble(24 + j, this.horizontalPositionUncertaintyMeters);
        hwBlob.putDouble(32 + j, this.verticalPositionUncertaintyMeters);
        hwBlob.putInt64(40 + j, this.toaGpsNanosecondsOfWeek);
        int size = this.satCorrections.size();
        long j2 = 48 + j;
        hwBlob.putInt32(56 + j, size);
        hwBlob.putBool(j + 60, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            this.satCorrections.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
