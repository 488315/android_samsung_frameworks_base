package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CardStatus {
    public android.hardware.radio.V1_0.CardStatus base = new android.hardware.radio.V1_0.CardStatus();
    public int physicalSlotId = 0;
    public String atr = new String();
    public String iccid = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CardStatus.class) {
            return false;
        }
        CardStatus cardStatus = (CardStatus) obj;
        return HidlSupport.deepEquals(this.base, cardStatus.base) && this.physicalSlotId == cardStatus.physicalSlotId && HidlSupport.deepEquals(this.atr, cardStatus.atr) && HidlSupport.deepEquals(this.iccid, cardStatus.iccid);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.physicalSlotId))), Integer.valueOf(HidlSupport.deepHashCode(this.atr)), Integer.valueOf(HidlSupport.deepHashCode(this.iccid)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .physicalSlotId = " + this.physicalSlotId + ", .atr = " + this.atr + ", .iccid = " + this.iccid + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final ArrayList<CardStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CardStatus> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CardStatus cardStatus = new CardStatus();
            cardStatus.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 80);
            arrayList.add(cardStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.physicalSlotId = hwBlob.getInt32(40 + j);
        long j2 = j + 48;
        this.atr = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r3.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 64;
        this.iccid = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r1.getBytes().length + 1, hwBlob.handle(), j3, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CardStatus> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(40 + j, this.physicalSlotId);
        hwBlob.putString(48 + j, this.atr);
        hwBlob.putString(j + 64, this.iccid);
    }
}
