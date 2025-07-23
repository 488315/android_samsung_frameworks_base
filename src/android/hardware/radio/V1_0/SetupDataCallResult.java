package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SetupDataCallResult {
    public int status = 0;
    public int suggestedRetryTime = 0;
    public int cid = 0;
    public int active = 0;
    public String type = new String();
    public String ifname = new String();
    public String addresses = new String();
    public String dnses = new String();
    public String gateways = new String();
    public String pcscf = new String();
    public int mtu = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SetupDataCallResult.class) {
            return false;
        }
        SetupDataCallResult setupDataCallResult = (SetupDataCallResult) obj;
        return this.status == setupDataCallResult.status && this.suggestedRetryTime == setupDataCallResult.suggestedRetryTime && this.cid == setupDataCallResult.cid && this.active == setupDataCallResult.active && HidlSupport.deepEquals(this.type, setupDataCallResult.type) && HidlSupport.deepEquals(this.ifname, setupDataCallResult.ifname) && HidlSupport.deepEquals(this.addresses, setupDataCallResult.addresses) && HidlSupport.deepEquals(this.dnses, setupDataCallResult.dnses) && HidlSupport.deepEquals(this.gateways, setupDataCallResult.gateways) && HidlSupport.deepEquals(this.pcscf, setupDataCallResult.pcscf) && this.mtu == setupDataCallResult.mtu;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.suggestedRetryTime))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cid))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.active))), Integer.valueOf(HidlSupport.deepHashCode(this.type)), Integer.valueOf(HidlSupport.deepHashCode(this.ifname)), Integer.valueOf(HidlSupport.deepHashCode(this.addresses)), Integer.valueOf(HidlSupport.deepHashCode(this.dnses)), Integer.valueOf(HidlSupport.deepHashCode(this.gateways)), Integer.valueOf(HidlSupport.deepHashCode(this.pcscf)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtu))));
    }

    public final String toString() {
        return "{.status = " + DataCallFailCause.toString(this.status) + ", .suggestedRetryTime = " + this.suggestedRetryTime + ", .cid = " + this.cid + ", .active = " + this.active + ", .type = " + this.type + ", .ifname = " + this.ifname + ", .addresses = " + this.addresses + ", .dnses = " + this.dnses + ", .gateways = " + this.gateways + ", .pcscf = " + this.pcscf + ", .mtu = " + this.mtu + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final ArrayList<SetupDataCallResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SetupDataCallResult> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SetupDataCallResult setupDataCallResult = new SetupDataCallResult();
            setupDataCallResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            arrayList.add(setupDataCallResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j);
        this.suggestedRetryTime = hwBlob.getInt32(j + 4);
        this.cid = hwBlob.getInt32(j + 8);
        this.active = hwBlob.getInt32(j + 12);
        long j2 = j + 16;
        this.type = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 32;
        this.ifname = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        long j4 = j + 48;
        this.addresses = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j4, false);
        long j5 = j + 64;
        this.dnses = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j5, false);
        long j6 = j + 80;
        this.gateways = hwBlob.getString(j6);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j6, false);
        long j7 = j + 96;
        this.pcscf = hwBlob.getString(j7);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j7, false);
        this.mtu = hwBlob.getInt32(j + 112);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SetupDataCallResult> arrayList) {
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
        hwBlob.putInt32(j, this.status);
        hwBlob.putInt32(4 + j, this.suggestedRetryTime);
        hwBlob.putInt32(8 + j, this.cid);
        hwBlob.putInt32(12 + j, this.active);
        hwBlob.putString(16 + j, this.type);
        hwBlob.putString(32 + j, this.ifname);
        hwBlob.putString(48 + j, this.addresses);
        hwBlob.putString(64 + j, this.dnses);
        hwBlob.putString(80 + j, this.gateways);
        hwBlob.putString(96 + j, this.pcscf);
        hwBlob.putInt32(j + 112, this.mtu);
    }
}
