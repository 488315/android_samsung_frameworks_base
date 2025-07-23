package android.hardware.radio.V1_6;

import android.hardware.radio.V1_0.CdmaSignalStrength;
import android.hardware.radio.V1_0.EvdoSignalStrength;
import android.hardware.radio.V1_0.GsmSignalStrength;
import android.hardware.radio.V1_2.TdscdmaSignalStrength;
import android.hardware.radio.V1_2.WcdmaSignalStrength;
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
    public TdscdmaSignalStrength tdscdma = new TdscdmaSignalStrength();
    public WcdmaSignalStrength wcdma = new WcdmaSignalStrength();
    public NrSignalStrength nr = new NrSignalStrength();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SignalStrength.class) {
            return false;
        }
        SignalStrength signalStrength = (SignalStrength) obj;
        return HidlSupport.deepEquals(this.gsm, signalStrength.gsm) && HidlSupport.deepEquals(this.cdma, signalStrength.cdma) && HidlSupport.deepEquals(this.evdo, signalStrength.evdo) && HidlSupport.deepEquals(this.lte, signalStrength.lte) && HidlSupport.deepEquals(this.tdscdma, signalStrength.tdscdma) && HidlSupport.deepEquals(this.wcdma, signalStrength.wcdma) && HidlSupport.deepEquals(this.nr, signalStrength.nr);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.gsm)), Integer.valueOf(HidlSupport.deepHashCode(this.cdma)), Integer.valueOf(HidlSupport.deepHashCode(this.evdo)), Integer.valueOf(HidlSupport.deepHashCode(this.lte)), Integer.valueOf(HidlSupport.deepHashCode(this.tdscdma)), Integer.valueOf(HidlSupport.deepHashCode(this.wcdma)), Integer.valueOf(HidlSupport.deepHashCode(this.nr)));
    }

    public final String toString() {
        return "{.gsm = " + this.gsm + ", .cdma = " + this.cdma + ", .evdo = " + this.evdo + ", .lte = " + this.lte + ", .tdscdma = " + this.tdscdma + ", .wcdma = " + this.wcdma + ", .nr = " + this.nr + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(136L), 0L);
    }

    public static final ArrayList<SignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SignalStrength> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 136, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SignalStrength signalStrength = new SignalStrength();
            signalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 136);
            arrayList.add(signalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.gsm.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.cdma.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        this.evdo.readEmbeddedFromParcel(hwParcel, hwBlob, 20 + j);
        this.lte.readEmbeddedFromParcel(hwParcel, hwBlob, 32 + j);
        this.tdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, 60 + j);
        this.wcdma.readEmbeddedFromParcel(hwParcel, hwBlob, 72 + j);
        this.nr.readEmbeddedFromParcel(hwParcel, hwBlob, j + 88);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(136);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 136);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 136);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.gsm.writeEmbeddedToBlob(hwBlob, j);
        this.cdma.writeEmbeddedToBlob(hwBlob, 12 + j);
        this.evdo.writeEmbeddedToBlob(hwBlob, 20 + j);
        this.lte.writeEmbeddedToBlob(hwBlob, 32 + j);
        this.tdscdma.writeEmbeddedToBlob(hwBlob, 60 + j);
        this.wcdma.writeEmbeddedToBlob(hwBlob, 72 + j);
        this.nr.writeEmbeddedToBlob(hwBlob, j + 88);
    }
}
