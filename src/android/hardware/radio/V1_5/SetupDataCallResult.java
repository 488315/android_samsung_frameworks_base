package android.hardware.radio.V1_5;

import android.hardware.radio.V1_4.DataCallFailCause;
import android.hardware.radio.V1_4.DataConnActiveStatus;
import android.hardware.radio.V1_4.PdpProtocolType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SetupDataCallResult {
    public int cause = 0;
    public int suggestedRetryTime = 0;
    public int cid = 0;
    public int active = 0;
    public int type = 0;
    public String ifname = new String();
    public ArrayList<LinkAddress> addresses = new ArrayList<>();
    public ArrayList<String> dnses = new ArrayList<>();
    public ArrayList<String> gateways = new ArrayList<>();
    public ArrayList<String> pcscf = new ArrayList<>();
    public int mtuV4 = 0;
    public int mtuV6 = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SetupDataCallResult.class) {
            return false;
        }
        SetupDataCallResult setupDataCallResult = (SetupDataCallResult) obj;
        return this.cause == setupDataCallResult.cause && this.suggestedRetryTime == setupDataCallResult.suggestedRetryTime && this.cid == setupDataCallResult.cid && this.active == setupDataCallResult.active && this.type == setupDataCallResult.type && HidlSupport.deepEquals(this.ifname, setupDataCallResult.ifname) && HidlSupport.deepEquals(this.addresses, setupDataCallResult.addresses) && HidlSupport.deepEquals(this.dnses, setupDataCallResult.dnses) && HidlSupport.deepEquals(this.gateways, setupDataCallResult.gateways) && HidlSupport.deepEquals(this.pcscf, setupDataCallResult.pcscf) && this.mtuV4 == setupDataCallResult.mtuV4 && this.mtuV6 == setupDataCallResult.mtuV6;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cause))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.suggestedRetryTime))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cid))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.active))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.ifname)), Integer.valueOf(HidlSupport.deepHashCode(this.addresses)), Integer.valueOf(HidlSupport.deepHashCode(this.dnses)), Integer.valueOf(HidlSupport.deepHashCode(this.gateways)), Integer.valueOf(HidlSupport.deepHashCode(this.pcscf)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtuV4))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtuV6))));
    }

    public final String toString() {
        return "{.cause = " + DataCallFailCause.toString(this.cause) + ", .suggestedRetryTime = " + this.suggestedRetryTime + ", .cid = " + this.cid + ", .active = " + DataConnActiveStatus.toString(this.active) + ", .type = " + PdpProtocolType.toString(this.type) + ", .ifname = " + this.ifname + ", .addresses = " + this.addresses + ", .dnses = " + this.dnses + ", .gateways = " + this.gateways + ", .pcscf = " + this.pcscf + ", .mtuV4 = " + this.mtuV4 + ", .mtuV6 = " + this.mtuV6 + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final ArrayList<SetupDataCallResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SetupDataCallResult> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SetupDataCallResult setupDataCallResult = new SetupDataCallResult();
            setupDataCallResult.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 112);
            arrayList.add(setupDataCallResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cause = hwBlob.getInt32(j);
        this.suggestedRetryTime = hwBlob.getInt32(j + 4);
        this.cid = hwBlob.getInt32(j + 8);
        this.active = hwBlob.getInt32(j + 12);
        this.type = hwBlob.getInt32(j + 16);
        long j2 = j + 24;
        this.ifname = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        int int32 = hwBlob.getInt32(j + 48);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, hwBlob.handle(), j + 40, true);
        this.addresses.clear();
        for (int i = 0; i < int32; i++) {
            LinkAddress linkAddress = new LinkAddress();
            linkAddress.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            this.addresses.add(linkAddress);
        }
        int int322 = hwBlob.getInt32(j + 64);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j + 56, true);
        this.dnses.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new String();
            int i3 = i2 * 16;
            String string = embeddedBuffer2.getString(i3);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, embeddedBuffer2.handle(), i3, false);
            this.dnses.add(string);
        }
        int int323 = hwBlob.getInt32(j + 80);
        HwBlob embeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 16, hwBlob.handle(), j + 72, true);
        this.gateways.clear();
        for (int i4 = 0; i4 < int323; i4++) {
            new String();
            int i5 = i4 * 16;
            String string2 = embeddedBuffer3.getString(i5);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, embeddedBuffer3.handle(), i5, false);
            this.gateways.add(string2);
        }
        int int324 = hwBlob.getInt32(j + 96);
        HwBlob embeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 16, hwBlob.handle(), j + 88, true);
        this.pcscf.clear();
        for (int i6 = 0; i6 < int324; i6++) {
            new String();
            int i7 = i6 * 16;
            String string3 = embeddedBuffer4.getString(i7);
            hwParcel.readEmbeddedBuffer(string3.getBytes().length + 1, embeddedBuffer4.handle(), i7, false);
            this.pcscf.add(string3);
        }
        this.mtuV4 = hwBlob.getInt32(j + 104);
        this.mtuV6 = hwBlob.getInt32(j + 108);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SetupDataCallResult> arrayList) {
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
        hwBlob.putInt32(j, this.cause);
        hwBlob.putInt32(4 + j, this.suggestedRetryTime);
        hwBlob.putInt32(8 + j, this.cid);
        hwBlob.putInt32(12 + j, this.active);
        hwBlob.putInt32(16 + j, this.type);
        hwBlob.putString(24 + j, this.ifname);
        int size = this.addresses.size();
        long j2 = 40 + j;
        hwBlob.putInt32(48 + j, size);
        hwBlob.putBool(52 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            this.addresses.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.dnses.size();
        long j3 = 56 + j;
        hwBlob.putInt32(64 + j, size2);
        hwBlob.putBool(68 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.dnses.get(i2));
        }
        hwBlob.putBlob(j3, hwBlob3);
        int size3 = this.gateways.size();
        long j4 = 72 + j;
        hwBlob.putInt32(80 + j, size3);
        hwBlob.putBool(84 + j, false);
        HwBlob hwBlob4 = new HwBlob(size3 * 16);
        for (int i3 = 0; i3 < size3; i3++) {
            hwBlob4.putString(i3 * 16, this.gateways.get(i3));
        }
        hwBlob.putBlob(j4, hwBlob4);
        int size4 = this.pcscf.size();
        long j5 = 88 + j;
        hwBlob.putInt32(96 + j, size4);
        hwBlob.putBool(100 + j, false);
        HwBlob hwBlob5 = new HwBlob(size4 * 16);
        for (int i4 = 0; i4 < size4; i4++) {
            hwBlob5.putString(i4 * 16, this.pcscf.get(i4));
        }
        hwBlob.putBlob(j5, hwBlob5);
        hwBlob.putInt32(104 + j, this.mtuV4);
        hwBlob.putInt32(j + 108, this.mtuV6);
    }
}
