package android.hardware.radio.V1_2;

import android.hardware.radio.V1_0.CdmaSignalStrength;
import android.hardware.radio.V1_0.EvdoSignalStrength;
import android.hardware.radio.V1_0.GsmSignalStrength;
import android.hardware.radio.V1_0.LteSignalStrength;
import android.hardware.radio.V1_0.TdScdmaSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SignalStrength {
    public GsmSignalStrength gsm = new GsmSignalStrength();
    public CdmaSignalStrength cdma = new CdmaSignalStrength();
    public EvdoSignalStrength evdo = new EvdoSignalStrength();
    public LteSignalStrength lte = new LteSignalStrength();
    public TdScdmaSignalStrength tdScdma = new TdScdmaSignalStrength();
    public WcdmaSignalStrength wcdma = new WcdmaSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SignalStrength.class) {
            return false;
        }
        SignalStrength signalStrength = (SignalStrength) obj;
        return HidlSupport.deepEquals(this.gsm, signalStrength.gsm) && HidlSupport.deepEquals(this.cdma, signalStrength.cdma) && HidlSupport.deepEquals(this.evdo, signalStrength.evdo) && HidlSupport.deepEquals(this.lte, signalStrength.lte) && HidlSupport.deepEquals(this.tdScdma, signalStrength.tdScdma) && HidlSupport.deepEquals(this.wcdma, signalStrength.wcdma);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.gsm)), Integer.valueOf(HidlSupport.deepHashCode(this.cdma)), Integer.valueOf(HidlSupport.deepHashCode(this.evdo)), Integer.valueOf(HidlSupport.deepHashCode(this.lte)), Integer.valueOf(HidlSupport.deepHashCode(this.tdScdma)), Integer.valueOf(HidlSupport.deepHashCode(this.wcdma)));
    }

    public final String toString() {
        return "{.gsm = " + this.gsm + ", .cdma = " + this.cdma + ", .evdo = " + this.evdo + ", .lte = " + this.lte + ", .tdScdma = " + this.tdScdma + ", .wcdma = " + this.wcdma + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(76L), 0L);
    }

    public static final ArrayList<SignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SignalStrength> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 76, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SignalStrength signalStrength = new SignalStrength();
            signalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 76);
            arrayList.add(signalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.gsm.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.cdma.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        this.evdo.readEmbeddedFromParcel(hwParcel, hwBlob, 20 + j);
        this.lte.readEmbeddedFromParcel(hwParcel, hwBlob, 32 + j);
        this.tdScdma.readEmbeddedFromParcel(hwParcel, hwBlob, 56 + j);
        this.wcdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 60);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(76);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 76);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 76);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.gsm.writeEmbeddedToBlob(hwBlob, j);
        this.cdma.writeEmbeddedToBlob(hwBlob, 12 + j);
        this.evdo.writeEmbeddedToBlob(hwBlob, 20 + j);
        this.lte.writeEmbeddedToBlob(hwBlob, 32 + j);
        this.tdScdma.writeEmbeddedToBlob(hwBlob, 56 + j);
        this.wcdma.writeEmbeddedToBlob(hwBlob, j + 60);
    }
}
