package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehEncodedUssd {
    public ArrayList<Byte> encodedUssd = new ArrayList<>();
    public int ussdLength = 0;
    public int dcsCode = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehEncodedUssd.class) {
            return false;
        }
        SehEncodedUssd sehEncodedUssd = (SehEncodedUssd) obj;
        return HidlSupport.deepEquals(this.encodedUssd, sehEncodedUssd.encodedUssd) && this.ussdLength == sehEncodedUssd.ussdLength && this.dcsCode == sehEncodedUssd.dcsCode;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.encodedUssd)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ussdLength))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.dcsCode))));
    }

    public final String toString() {
        return "{.encodedUssd = " + this.encodedUssd + ", .ussdLength = " + this.ussdLength + ", .dcsCode = " + this.dcsCode + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<SehEncodedUssd> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehEncodedUssd> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehEncodedUssd sehEncodedUssd = new SehEncodedUssd();
            sehEncodedUssd.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(sehEncodedUssd);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(8 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32, hwBlob.handle(), j, true);
        this.encodedUssd.clear();
        for (int i = 0; i < int32; i++) {
            this.encodedUssd.add(Byte.valueOf(readEmbeddedBuffer.getInt8(i)));
        }
        this.ussdLength = hwBlob.getInt32(16 + j);
        this.dcsCode = hwBlob.getInt32(20 + j);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehEncodedUssd> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        int size = this.encodedUssd.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i, this.encodedUssd.get(i).byteValue());
        }
        hwBlob.putBlob(j, hwBlob2);
        hwBlob.putInt32(16 + j, this.ussdLength);
        hwBlob.putInt32(j + 20, this.dcsCode);
    }
}
