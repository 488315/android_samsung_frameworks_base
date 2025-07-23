package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class NrIndicators {
    public boolean isEndcAvailable = false;
    public boolean isDcNrRestricted = false;
    public boolean isNrAvailable = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != NrIndicators.class) {
            return false;
        }
        NrIndicators nrIndicators = (NrIndicators) obj;
        return this.isEndcAvailable == nrIndicators.isEndcAvailable && this.isDcNrRestricted == nrIndicators.isDcNrRestricted && this.isNrAvailable == nrIndicators.isNrAvailable;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isEndcAvailable))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isDcNrRestricted))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isNrAvailable))));
    }

    public final String toString() {
        return "{.isEndcAvailable = " + this.isEndcAvailable + ", .isDcNrRestricted = " + this.isDcNrRestricted + ", .isNrAvailable = " + this.isNrAvailable + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(3L), 0L);
    }

    public static final ArrayList<NrIndicators> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NrIndicators> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 3, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            NrIndicators nrIndicators = new NrIndicators();
            nrIndicators.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 3);
            arrayList.add(nrIndicators);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.isEndcAvailable = hwBlob.getBool(j);
        this.isDcNrRestricted = hwBlob.getBool(1 + j);
        this.isNrAvailable = hwBlob.getBool(j + 2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(3);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NrIndicators> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 3);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 3);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putBool(j, this.isEndcAvailable);
        hwBlob.putBool(1 + j, this.isDcNrRestricted);
        hwBlob.putBool(j + 2, this.isNrAvailable);
    }
}
