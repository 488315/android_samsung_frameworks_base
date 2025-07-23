package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CallForwardInfo {
    public int status = 0;
    public int reason = 0;
    public int serviceClass = 0;
    public int toa = 0;
    public String number = new String();
    public int timeSeconds = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CallForwardInfo.class) {
            return false;
        }
        CallForwardInfo callForwardInfo = (CallForwardInfo) obj;
        return this.status == callForwardInfo.status && this.reason == callForwardInfo.reason && this.serviceClass == callForwardInfo.serviceClass && this.toa == callForwardInfo.toa && HidlSupport.deepEquals(this.number, callForwardInfo.number) && this.timeSeconds == callForwardInfo.timeSeconds;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.reason))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.serviceClass))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.toa))), Integer.valueOf(HidlSupport.deepHashCode(this.number)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.timeSeconds))));
    }

    public final String toString() {
        return "{.status = " + CallForwardInfoStatus.toString(this.status) + ", .reason = " + this.reason + ", .serviceClass = " + this.serviceClass + ", .toa = " + this.toa + ", .number = " + this.number + ", .timeSeconds = " + this.timeSeconds + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<CallForwardInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CallForwardInfo> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CallForwardInfo callForwardInfo = new CallForwardInfo();
            callForwardInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(callForwardInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j);
        this.reason = hwBlob.getInt32(4 + j);
        this.serviceClass = hwBlob.getInt32(8 + j);
        this.toa = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.timeSeconds = hwBlob.getInt32(j + 32);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CallForwardInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.status);
        hwBlob.putInt32(4 + j, this.reason);
        hwBlob.putInt32(8 + j, this.serviceClass);
        hwBlob.putInt32(12 + j, this.toa);
        hwBlob.putString(16 + j, this.number);
        hwBlob.putInt32(j + 32, this.timeSeconds);
    }
}
