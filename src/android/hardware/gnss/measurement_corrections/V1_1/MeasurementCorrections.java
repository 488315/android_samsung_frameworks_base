package android.hardware.gnss.measurement_corrections.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MeasurementCorrections {
    public android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections v1_0 = new android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections();
    public boolean hasEnvironmentBearing = false;
    public float environmentBearingDegrees = 0.0f;
    public float environmentBearingUncertaintyDegrees = 0.0f;
    public ArrayList<SingleSatCorrection> satCorrections = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != MeasurementCorrections.class) {
            return false;
        }
        MeasurementCorrections measurementCorrections = (MeasurementCorrections) obj;
        return HidlSupport.deepEquals(this.v1_0, measurementCorrections.v1_0) && this.hasEnvironmentBearing == measurementCorrections.hasEnvironmentBearing && this.environmentBearingDegrees == measurementCorrections.environmentBearingDegrees && this.environmentBearingUncertaintyDegrees == measurementCorrections.environmentBearingUncertaintyDegrees && HidlSupport.deepEquals(this.satCorrections, measurementCorrections.satCorrections);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.v1_0)), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.hasEnvironmentBearing))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.environmentBearingDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.environmentBearingUncertaintyDegrees))), Integer.valueOf(HidlSupport.deepHashCode(this.satCorrections)));
    }

    public final String toString() {
        return "{.v1_0 = " + this.v1_0 + ", .hasEnvironmentBearing = " + this.hasEnvironmentBearing + ", .environmentBearingDegrees = " + this.environmentBearingDegrees + ", .environmentBearingUncertaintyDegrees = " + this.environmentBearingUncertaintyDegrees + ", .satCorrections = " + this.satCorrections + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final ArrayList<MeasurementCorrections> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<MeasurementCorrections> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            MeasurementCorrections measurementCorrections = new MeasurementCorrections();
            measurementCorrections.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(measurementCorrections);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.v1_0.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.hasEnvironmentBearing = hwBlob.getBool(64 + j);
        this.environmentBearingDegrees = hwBlob.getFloat(68 + j);
        this.environmentBearingUncertaintyDegrees = hwBlob.getFloat(72 + j);
        long j2 = j + 80;
        int int32 = hwBlob.getInt32(j + 88);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, hwBlob.handle(), j2, true);
        this.satCorrections.clear();
        for (int i = 0; i < int32; i++) {
            SingleSatCorrection singleSatCorrection = new SingleSatCorrection();
            singleSatCorrection.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            this.satCorrections.add(singleSatCorrection);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<MeasurementCorrections> arrayList) {
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
        this.v1_0.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putBool(64 + j, this.hasEnvironmentBearing);
        hwBlob.putFloat(68 + j, this.environmentBearingDegrees);
        hwBlob.putFloat(72 + j, this.environmentBearingUncertaintyDegrees);
        int size = this.satCorrections.size();
        long j2 = 80 + j;
        hwBlob.putInt32(88 + j, size);
        hwBlob.putBool(j + 92, false);
        HwBlob hwBlob2 = new HwBlob(size * 64);
        for (int i = 0; i < size; i++) {
            this.satCorrections.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
