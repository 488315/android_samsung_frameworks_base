package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaCallWaiting {
    public String number = new String();
    public int numberPresentation = 0;
    public String name = new String();
    public CdmaSignalInfoRecord signalInfoRecord = new CdmaSignalInfoRecord();
    public int numberType = 0;
    public int numberPlan = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaCallWaiting.class) {
            return false;
        }
        CdmaCallWaiting cdmaCallWaiting = (CdmaCallWaiting) obj;
        return HidlSupport.deepEquals(this.number, cdmaCallWaiting.number) && this.numberPresentation == cdmaCallWaiting.numberPresentation && HidlSupport.deepEquals(this.name, cdmaCallWaiting.name) && HidlSupport.deepEquals(this.signalInfoRecord, cdmaCallWaiting.signalInfoRecord) && this.numberType == cdmaCallWaiting.numberType && this.numberPlan == cdmaCallWaiting.numberPlan;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.number)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberPresentation))), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(this.signalInfoRecord)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numberPlan))));
    }

    public final String toString() {
        return "{.number = " + this.number + ", .numberPresentation = " + CdmaCallWaitingNumberPresentation.toString(this.numberPresentation) + ", .name = " + this.name + ", .signalInfoRecord = " + this.signalInfoRecord + ", .numberType = " + CdmaCallWaitingNumberType.toString(this.numberType) + ", .numberPlan = " + CdmaCallWaitingNumberPlan.toString(this.numberPlan) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<CdmaCallWaiting> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaCallWaiting> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaCallWaiting cdmaCallWaiting = new CdmaCallWaiting();
            cdmaCallWaiting.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 56);
            arrayList.add(cdmaCallWaiting);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.number = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        this.numberPresentation = hwBlob.getInt32(j + 16);
        long j2 = j + 24;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.signalInfoRecord.readEmbeddedFromParcel(hwParcel, hwBlob, j + 40);
        this.numberType = hwBlob.getInt32(j + 44);
        this.numberPlan = hwBlob.getInt32(j + 48);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaCallWaiting> arrayList) {
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
        hwBlob.putString(j, this.number);
        hwBlob.putInt32(16 + j, this.numberPresentation);
        hwBlob.putString(24 + j, this.name);
        this.signalInfoRecord.writeEmbeddedToBlob(hwBlob, 40 + j);
        hwBlob.putInt32(44 + j, this.numberType);
        hwBlob.putInt32(j + 48, this.numberPlan);
    }
}
