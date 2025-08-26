package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SelectUiccSub {
    public int slot = 0;
    public int appIndex = 0;
    public int subType = 0;
    public int actStatus = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SelectUiccSub.class) {
            return false;
        }
        SelectUiccSub selectUiccSub = (SelectUiccSub) obj;
        return this.slot == selectUiccSub.slot && this.appIndex == selectUiccSub.appIndex && this.subType == selectUiccSub.subType && this.actStatus == selectUiccSub.actStatus;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.slot))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.appIndex))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.subType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.actStatus))));
    }

    public final String toString() {
        return "{.slot = " + this.slot + ", .appIndex = " + this.appIndex + ", .subType = " + SubscriptionType.toString(this.subType) + ", .actStatus = " + UiccSubActStatus.toString(this.actStatus) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final ArrayList<SelectUiccSub> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SelectUiccSub> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SelectUiccSub selectUiccSub = new SelectUiccSub();
            selectUiccSub.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 16);
            arrayList.add(selectUiccSub);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.slot = hwBlob.getInt32(j);
        this.appIndex = hwBlob.getInt32(4 + j);
        this.subType = hwBlob.getInt32(8 + j);
        this.actStatus = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SelectUiccSub> arrayList) {
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
        hwBlob.putInt32(j, this.slot);
        hwBlob.putInt32(4 + j, this.appIndex);
        hwBlob.putInt32(8 + j, this.subType);
        hwBlob.putInt32(j + 12, this.actStatus);
    }
}
