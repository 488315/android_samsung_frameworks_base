package android.hardware.radio.V1_3;

import android.hardware.radio.V1_0.RadioError;
import android.hardware.radio.V1_0.RadioResponseType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RadioResponseInfoModem {
    public int type = 0;
    public int serial = 0;
    public int error = 0;
    public boolean isEnabled = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RadioResponseInfoModem.class) {
            return false;
        }
        RadioResponseInfoModem radioResponseInfoModem = (RadioResponseInfoModem) obj;
        return this.type == radioResponseInfoModem.type && this.serial == radioResponseInfoModem.serial && this.error == radioResponseInfoModem.error && this.isEnabled == radioResponseInfoModem.isEnabled;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.serial))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.error))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isEnabled))));
    }

    public final String toString() {
        return "{.type = " + RadioResponseType.toString(this.type) + ", .serial = " + this.serial + ", .error = " + RadioError.toString(this.error) + ", .isEnabled = " + this.isEnabled + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<RadioResponseInfoModem> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioResponseInfoModem> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RadioResponseInfoModem radioResponseInfoModem = new RadioResponseInfoModem();
            radioResponseInfoModem.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 16);
            arrayList.add(radioResponseInfoModem);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j);
        this.serial = hwBlob.getInt32(4 + j);
        this.error = hwBlob.getInt32(8 + j);
        this.isEnabled = hwBlob.getBool(j + 12);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioResponseInfoModem> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.type);
        hwBlob.putInt32(4 + j, this.serial);
        hwBlob.putInt32(8 + j, this.error);
        hwBlob.putBool(j + 12, this.isEnabled);
    }
}
