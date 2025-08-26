package android.hardware.gnss.V2_1;

import android.hardware.gnss.V2_0.GnssConstellationType;
import android.hardware.scontext.SContextConstants;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GnssSignalType {
    public byte constellation = 0;
    public double carrierFrequencyHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public String codeType = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != GnssSignalType.class) {
            return false;
        }
        GnssSignalType gnssSignalType = (GnssSignalType) obj;
        return this.constellation == gnssSignalType.constellation && this.carrierFrequencyHz == gnssSignalType.carrierFrequencyHz && HidlSupport.deepEquals(this.codeType, gnssSignalType.codeType);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.constellation))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.carrierFrequencyHz))), Integer.valueOf(HidlSupport.deepHashCode(this.codeType)));
    }

    public final String toString() {
        return "{.constellation = " + GnssConstellationType.toString(this.constellation) + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .codeType = " + this.codeType + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<GnssSignalType> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GnssSignalType> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            GnssSignalType gnssSignalType = new GnssSignalType();
            gnssSignalType.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(gnssSignalType);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.constellation = hwBlob.getInt8(j);
        this.carrierFrequencyHz = hwBlob.getDouble(8 + j);
        long j2 = j + 16;
        this.codeType = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssSignalType> arrayList) {
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
        hwBlob.putInt8(j, this.constellation);
        hwBlob.putDouble(8 + j, this.carrierFrequencyHz);
        hwBlob.putString(j + 16, this.codeType);
    }
}
