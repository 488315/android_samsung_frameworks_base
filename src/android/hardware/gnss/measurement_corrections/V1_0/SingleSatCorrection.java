package android.hardware.gnss.measurement_corrections.V1_0;

import android.hardware.gnss.V1_0.GnssConstellationType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SingleSatCorrection {
    public short singleSatCorrectionFlags;
    public byte constellation = 0;
    public short svid = 0;
    public float carrierFrequencyHz = 0.0f;
    public float probSatIsLos = 0.0f;
    public float excessPathLengthMeters = 0.0f;
    public float excessPathLengthUncertaintyMeters = 0.0f;
    public ReflectingPlane reflectingPlane = new ReflectingPlane();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SingleSatCorrection.class) {
            return false;
        }
        SingleSatCorrection singleSatCorrection = (SingleSatCorrection) obj;
        return HidlSupport.deepEquals(Short.valueOf(this.singleSatCorrectionFlags), Short.valueOf(singleSatCorrection.singleSatCorrectionFlags)) && this.constellation == singleSatCorrection.constellation && this.svid == singleSatCorrection.svid && this.carrierFrequencyHz == singleSatCorrection.carrierFrequencyHz && this.probSatIsLos == singleSatCorrection.probSatIsLos && this.excessPathLengthMeters == singleSatCorrection.excessPathLengthMeters && this.excessPathLengthUncertaintyMeters == singleSatCorrection.excessPathLengthUncertaintyMeters && HidlSupport.deepEquals(this.reflectingPlane, singleSatCorrection.reflectingPlane);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.singleSatCorrectionFlags))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.constellation))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.svid))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.carrierFrequencyHz))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.probSatIsLos))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.excessPathLengthMeters))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.excessPathLengthUncertaintyMeters))), Integer.valueOf(HidlSupport.deepHashCode(this.reflectingPlane)));
    }

    public final String toString() {
        return "{.singleSatCorrectionFlags = " + GnssSingleSatCorrectionFlags.dumpBitfield(this.singleSatCorrectionFlags) + ", .constellation = " + GnssConstellationType.toString(this.constellation) + ", .svid = " + ((int) this.svid) + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .probSatIsLos = " + this.probSatIsLos + ", .excessPathLengthMeters = " + this.excessPathLengthMeters + ", .excessPathLengthUncertaintyMeters = " + this.excessPathLengthUncertaintyMeters + ", .reflectingPlane = " + this.reflectingPlane + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<SingleSatCorrection> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SingleSatCorrection> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SingleSatCorrection singleSatCorrection = new SingleSatCorrection();
            singleSatCorrection.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(singleSatCorrection);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.singleSatCorrectionFlags = hwBlob.getInt16(j);
        this.constellation = hwBlob.getInt8(2 + j);
        this.svid = hwBlob.getInt16(4 + j);
        this.carrierFrequencyHz = hwBlob.getFloat(8 + j);
        this.probSatIsLos = hwBlob.getFloat(12 + j);
        this.excessPathLengthMeters = hwBlob.getFloat(16 + j);
        this.excessPathLengthUncertaintyMeters = hwBlob.getFloat(20 + j);
        this.reflectingPlane.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SingleSatCorrection> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt16(j, this.singleSatCorrectionFlags);
        hwBlob.putInt8(2 + j, this.constellation);
        hwBlob.putInt16(4 + j, this.svid);
        hwBlob.putFloat(8 + j, this.carrierFrequencyHz);
        hwBlob.putFloat(12 + j, this.probSatIsLos);
        hwBlob.putFloat(16 + j, this.excessPathLengthMeters);
        hwBlob.putFloat(20 + j, this.excessPathLengthUncertaintyMeters);
        this.reflectingPlane.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
