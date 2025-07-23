package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CardStatus {
    public android.hardware.radio.V1_4.CardStatus base = new android.hardware.radio.V1_4.CardStatus();
    public ArrayList<AppStatus> applications = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CardStatus.class) {
            return false;
        }
        CardStatus cardStatus = (CardStatus) obj;
        return HidlSupport.deepEquals(this.base, cardStatus.base) && HidlSupport.deepEquals(this.applications, cardStatus.applications);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.applications)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .applications = " + this.applications + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final ArrayList<CardStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CardStatus> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CardStatus cardStatus = new CardStatus();
            cardStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            arrayList.add(cardStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        long j2 = j + 96;
        int int32 = hwBlob.getInt32(j + 104);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, hwBlob.handle(), j2, true);
        this.applications.clear();
        for (int i = 0; i < int32; i++) {
            AppStatus appStatus = new AppStatus();
            appStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            this.applications.add(appStatus);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CardStatus> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        int size = this.applications.size();
        long j2 = 96 + j;
        hwBlob.putInt32(104 + j, size);
        hwBlob.putBool(j + 108, false);
        HwBlob hwBlob2 = new HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            this.applications.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(j2, hwBlob2);
    }
}
