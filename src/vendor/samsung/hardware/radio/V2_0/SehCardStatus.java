package vendor.samsung.hardware.radio.V2_0;

import android.hardware.radio.V1_0.CardState;
import android.hardware.radio.V1_0.PinState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCardStatus {
    public int cardState = 0;
    public int universalPinState = 0;
    public int gsmUmtsSubscriptionAppIndex = 0;
    public int cdmaSubscriptionAppIndex = 0;
    public int imsSubscriptionAppIndex = 0;
    public ArrayList<SehAppStatus> applications = new ArrayList<>();
    public int physicalSlotId = 0;
    public String atr = new String();
    public String iccid = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehCardStatus.class) {
            return false;
        }
        SehCardStatus sehCardStatus = (SehCardStatus) obj;
        return this.cardState == sehCardStatus.cardState && this.universalPinState == sehCardStatus.universalPinState && this.gsmUmtsSubscriptionAppIndex == sehCardStatus.gsmUmtsSubscriptionAppIndex && this.cdmaSubscriptionAppIndex == sehCardStatus.cdmaSubscriptionAppIndex && this.imsSubscriptionAppIndex == sehCardStatus.imsSubscriptionAppIndex && HidlSupport.deepEquals(this.applications, sehCardStatus.applications) && this.physicalSlotId == sehCardStatus.physicalSlotId && HidlSupport.deepEquals(this.atr, sehCardStatus.atr) && HidlSupport.deepEquals(this.iccid, sehCardStatus.iccid);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cardState))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.universalPinState))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.gsmUmtsSubscriptionAppIndex))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cdmaSubscriptionAppIndex))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.imsSubscriptionAppIndex))), Integer.valueOf(HidlSupport.deepHashCode(this.applications)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.physicalSlotId))), Integer.valueOf(HidlSupport.deepHashCode(this.atr)), Integer.valueOf(HidlSupport.deepHashCode(this.iccid)));
    }

    public final String toString() {
        return "{.cardState = " + CardState.toString(this.cardState) + ", .universalPinState = " + PinState.toString(this.universalPinState) + ", .gsmUmtsSubscriptionAppIndex = " + this.gsmUmtsSubscriptionAppIndex + ", .cdmaSubscriptionAppIndex = " + this.cdmaSubscriptionAppIndex + ", .imsSubscriptionAppIndex = " + this.imsSubscriptionAppIndex + ", .applications = " + this.applications + ", .physicalSlotId = " + this.physicalSlotId + ", .atr = " + this.atr + ", .iccid = " + this.iccid + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final ArrayList<SehCardStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehCardStatus> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehCardStatus sehCardStatus = new SehCardStatus();
            sehCardStatus.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 80);
            arrayList.add(sehCardStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cardState = hwBlob.getInt32(j);
        this.universalPinState = hwBlob.getInt32(4 + j);
        this.gsmUmtsSubscriptionAppIndex = hwBlob.getInt32(8 + j);
        this.cdmaSubscriptionAppIndex = hwBlob.getInt32(12 + j);
        this.imsSubscriptionAppIndex = hwBlob.getInt32(16 + j);
        int int32 = hwBlob.getInt32(32 + j);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, hwBlob.handle(), j + 24, true);
        this.applications.clear();
        for (int i = 0; i < int32; i++) {
            SehAppStatus sehAppStatus = new SehAppStatus();
            sehAppStatus.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            this.applications.add(sehAppStatus);
        }
        this.physicalSlotId = hwBlob.getInt32(40 + j);
        long j2 = j + 48;
        this.atr = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r11.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 64;
        this.iccid = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r11.getBytes().length + 1, hwBlob.handle(), j3, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehCardStatus> arrayList) {
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
        hwBlob.putInt32(j, this.cardState);
        hwBlob.putInt32(4 + j, this.universalPinState);
        hwBlob.putInt32(8 + j, this.gsmUmtsSubscriptionAppIndex);
        hwBlob.putInt32(12 + j, this.cdmaSubscriptionAppIndex);
        hwBlob.putInt32(16 + j, this.imsSubscriptionAppIndex);
        int size = this.applications.size();
        long j2 = 24 + j;
        hwBlob.putInt32(32 + j, size);
        hwBlob.putBool(36 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            this.applications.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putInt32(40 + j, this.physicalSlotId);
        hwBlob.putString(48 + j, this.atr);
        hwBlob.putString(j + 64, this.iccid);
    }
}
