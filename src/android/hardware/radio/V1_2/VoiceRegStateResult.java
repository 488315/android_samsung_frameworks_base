package android.hardware.radio.V1_2;

import android.hardware.radio.V1_0.RegState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class VoiceRegStateResult {
    public int regState = 0;
    public int rat = 0;
    public boolean cssSupported = false;
    public int roamingIndicator = 0;
    public int systemIsInPrl = 0;
    public int defaultRoamingIndicator = 0;
    public int reasonForDenial = 0;
    public CellIdentity cellIdentity = new CellIdentity();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != VoiceRegStateResult.class) {
            return false;
        }
        VoiceRegStateResult voiceRegStateResult = (VoiceRegStateResult) obj;
        return this.regState == voiceRegStateResult.regState && this.rat == voiceRegStateResult.rat && this.cssSupported == voiceRegStateResult.cssSupported && this.roamingIndicator == voiceRegStateResult.roamingIndicator && this.systemIsInPrl == voiceRegStateResult.systemIsInPrl && this.defaultRoamingIndicator == voiceRegStateResult.defaultRoamingIndicator && this.reasonForDenial == voiceRegStateResult.reasonForDenial && HidlSupport.deepEquals(this.cellIdentity, voiceRegStateResult.cellIdentity);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.regState))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.cssSupported))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.roamingIndicator))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.systemIsInPrl))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.defaultRoamingIndicator))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.reasonForDenial))), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentity)));
    }

    public final String toString() {
        return "{.regState = " + RegState.toString(this.regState) + ", .rat = " + this.rat + ", .cssSupported = " + this.cssSupported + ", .roamingIndicator = " + this.roamingIndicator + ", .systemIsInPrl = " + this.systemIsInPrl + ", .defaultRoamingIndicator = " + this.defaultRoamingIndicator + ", .reasonForDenial = " + this.reasonForDenial + ", .cellIdentity = " + this.cellIdentity + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final ArrayList<VoiceRegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<VoiceRegStateResult> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            VoiceRegStateResult voiceRegStateResult = new VoiceRegStateResult();
            voiceRegStateResult.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 120);
            arrayList.add(voiceRegStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.regState = hwBlob.getInt32(j);
        this.rat = hwBlob.getInt32(4 + j);
        this.cssSupported = hwBlob.getBool(8 + j);
        this.roamingIndicator = hwBlob.getInt32(12 + j);
        this.systemIsInPrl = hwBlob.getInt32(16 + j);
        this.defaultRoamingIndicator = hwBlob.getInt32(20 + j);
        this.reasonForDenial = hwBlob.getInt32(24 + j);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, j + 32);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<VoiceRegStateResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.regState);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putBool(8 + j, this.cssSupported);
        hwBlob.putInt32(12 + j, this.roamingIndicator);
        hwBlob.putInt32(16 + j, this.systemIsInPrl);
        hwBlob.putInt32(20 + j, this.defaultRoamingIndicator);
        hwBlob.putInt32(24 + j, this.reasonForDenial);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, j + 32);
    }
}
