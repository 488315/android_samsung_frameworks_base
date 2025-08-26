package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.RegState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehExtendedRegStateResult {
    public boolean isValid = false;
    public int snapshotStatus = 0;
    public int unprocessedDataRegState = 0;
    public int unprocessedDataRat = 0;
    public int mobileOptionalRat = 0;
    public int imsEmergencyCallBarring = 0;
    public int unprocessedVoiceRegState = 0;
    public boolean isPsOnlyReg = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehExtendedRegStateResult.class) {
            return false;
        }
        SehExtendedRegStateResult sehExtendedRegStateResult = (SehExtendedRegStateResult) obj;
        return this.isValid == sehExtendedRegStateResult.isValid && this.snapshotStatus == sehExtendedRegStateResult.snapshotStatus && this.unprocessedDataRegState == sehExtendedRegStateResult.unprocessedDataRegState && this.unprocessedDataRat == sehExtendedRegStateResult.unprocessedDataRat && this.mobileOptionalRat == sehExtendedRegStateResult.mobileOptionalRat && this.imsEmergencyCallBarring == sehExtendedRegStateResult.imsEmergencyCallBarring && this.unprocessedVoiceRegState == sehExtendedRegStateResult.unprocessedVoiceRegState && this.isPsOnlyReg == sehExtendedRegStateResult.isPsOnlyReg;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isValid))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.snapshotStatus))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.unprocessedDataRegState))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.unprocessedDataRat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mobileOptionalRat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.imsEmergencyCallBarring))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.unprocessedVoiceRegState))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isPsOnlyReg))));
    }

    public final String toString() {
        return "{.isValid = " + this.isValid + ", .snapshotStatus = " + this.snapshotStatus + ", .unprocessedDataRegState = " + RegState.toString(this.unprocessedDataRegState) + ", .unprocessedDataRat = " + this.unprocessedDataRat + ", .mobileOptionalRat = " + this.mobileOptionalRat + ", .imsEmergencyCallBarring = " + this.imsEmergencyCallBarring + ", .unprocessedVoiceRegState = " + RegState.toString(this.unprocessedVoiceRegState) + ", .isPsOnlyReg = " + this.isPsOnlyReg + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SehExtendedRegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehExtendedRegStateResult> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehExtendedRegStateResult sehExtendedRegStateResult = new SehExtendedRegStateResult();
            sehExtendedRegStateResult.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 32);
            arrayList.add(sehExtendedRegStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.isValid = hwBlob.getBool(j);
        this.snapshotStatus = hwBlob.getInt32(4 + j);
        this.unprocessedDataRegState = hwBlob.getInt32(8 + j);
        this.unprocessedDataRat = hwBlob.getInt32(12 + j);
        this.mobileOptionalRat = hwBlob.getInt32(16 + j);
        this.imsEmergencyCallBarring = hwBlob.getInt32(20 + j);
        this.unprocessedVoiceRegState = hwBlob.getInt32(24 + j);
        this.isPsOnlyReg = hwBlob.getBool(j + 28);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehExtendedRegStateResult> arrayList) {
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
        hwBlob.putBool(j, this.isValid);
        hwBlob.putInt32(4 + j, this.snapshotStatus);
        hwBlob.putInt32(8 + j, this.unprocessedDataRegState);
        hwBlob.putInt32(12 + j, this.unprocessedDataRat);
        hwBlob.putInt32(16 + j, this.mobileOptionalRat);
        hwBlob.putInt32(20 + j, this.imsEmergencyCallBarring);
        hwBlob.putInt32(24 + j, this.unprocessedVoiceRegState);
        hwBlob.putBool(j + 28, this.isPsOnlyReg);
    }
}
