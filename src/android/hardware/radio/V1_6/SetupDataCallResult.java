package android.hardware.radio.V1_6;

import android.hardware.radio.V1_4.DataConnActiveStatus;
import android.hardware.radio.V1_4.PdpProtocolType;
import android.hardware.radio.V1_5.LinkAddress;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SetupDataCallResult {
    public int cause = 0;
    public long suggestedRetryTime = 0;
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
    public Qos defaultQos = new Qos();
    public ArrayList<QosSession> qosSessions = new ArrayList<>();
    public byte handoverFailureMode = 0;
    public int pduSessionId = 0;
    public OptionalSliceInfo sliceInfo = new OptionalSliceInfo();
    public ArrayList<TrafficDescriptor> trafficDescriptors = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SetupDataCallResult.class) {
            return false;
        }
        SetupDataCallResult setupDataCallResult = (SetupDataCallResult) obj;
        return this.cause == setupDataCallResult.cause && this.suggestedRetryTime == setupDataCallResult.suggestedRetryTime && this.cid == setupDataCallResult.cid && this.active == setupDataCallResult.active && this.type == setupDataCallResult.type && HidlSupport.deepEquals(this.ifname, setupDataCallResult.ifname) && HidlSupport.deepEquals(this.addresses, setupDataCallResult.addresses) && HidlSupport.deepEquals(this.dnses, setupDataCallResult.dnses) && HidlSupport.deepEquals(this.gateways, setupDataCallResult.gateways) && HidlSupport.deepEquals(this.pcscf, setupDataCallResult.pcscf) && this.mtuV4 == setupDataCallResult.mtuV4 && this.mtuV6 == setupDataCallResult.mtuV6 && HidlSupport.deepEquals(this.defaultQos, setupDataCallResult.defaultQos) && HidlSupport.deepEquals(this.qosSessions, setupDataCallResult.qosSessions) && this.handoverFailureMode == setupDataCallResult.handoverFailureMode && this.pduSessionId == setupDataCallResult.pduSessionId && HidlSupport.deepEquals(this.sliceInfo, setupDataCallResult.sliceInfo) && HidlSupport.deepEquals(this.trafficDescriptors, setupDataCallResult.trafficDescriptors);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cause))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.suggestedRetryTime))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cid))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.active))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.ifname)), Integer.valueOf(HidlSupport.deepHashCode(this.addresses)), Integer.valueOf(HidlSupport.deepHashCode(this.dnses)), Integer.valueOf(HidlSupport.deepHashCode(this.gateways)), Integer.valueOf(HidlSupport.deepHashCode(this.pcscf)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtuV4))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtuV6))), Integer.valueOf(HidlSupport.deepHashCode(this.defaultQos)), Integer.valueOf(HidlSupport.deepHashCode(this.qosSessions)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.handoverFailureMode))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pduSessionId))), Integer.valueOf(HidlSupport.deepHashCode(this.sliceInfo)), Integer.valueOf(HidlSupport.deepHashCode(this.trafficDescriptors)));
    }

    public final String toString() {
        return "{.cause = " + DataCallFailCause.toString(this.cause) + ", .suggestedRetryTime = " + this.suggestedRetryTime + ", .cid = " + this.cid + ", .active = " + DataConnActiveStatus.toString(this.active) + ", .type = " + PdpProtocolType.toString(this.type) + ", .ifname = " + this.ifname + ", .addresses = " + this.addresses + ", .dnses = " + this.dnses + ", .gateways = " + this.gateways + ", .pcscf = " + this.pcscf + ", .mtuV4 = " + this.mtuV4 + ", .mtuV6 = " + this.mtuV6 + ", .defaultQos = " + this.defaultQos + ", .qosSessions = " + this.qosSessions + ", .handoverFailureMode = " + HandoverFailureMode.toString(this.handoverFailureMode) + ", .pduSessionId = " + this.pduSessionId + ", .sliceInfo = " + this.sliceInfo + ", .trafficDescriptors = " + this.trafficDescriptors + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(216L), 0L);
    }

    public static final ArrayList<SetupDataCallResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SetupDataCallResult> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 216, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SetupDataCallResult setupDataCallResult = new SetupDataCallResult();
            setupDataCallResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 216);
            arrayList.add(setupDataCallResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.cause = hwBlob.getInt32(j);
        this.suggestedRetryTime = hwBlob.getInt64(j + 8);
        this.cid = hwBlob.getInt32(j + 16);
        this.active = hwBlob.getInt32(j + 20);
        this.type = hwBlob.getInt32(j + 24);
        long j2 = j + 32;
        this.ifname = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        int int32 = hwBlob.getInt32(j + 56);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, hwBlob.handle(), j + 48, true);
        this.addresses.clear();
        for (int i = 0; i < int32; i++) {
            LinkAddress linkAddress = new LinkAddress();
            linkAddress.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            this.addresses.add(linkAddress);
        }
        int int322 = hwBlob.getInt32(j + 72);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j + 64, true);
        this.dnses.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new String();
            int i3 = i2 * 16;
            String string = readEmbeddedBuffer2.getString(i3);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer2.handle(), i3, false);
            this.dnses.add(string);
        }
        int int323 = hwBlob.getInt32(j + 88);
        HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 16, hwBlob.handle(), j + 80, true);
        this.gateways.clear();
        for (int i4 = 0; i4 < int323; i4++) {
            new String();
            int i5 = i4 * 16;
            String string2 = readEmbeddedBuffer3.getString(i5);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, readEmbeddedBuffer3.handle(), i5, false);
            this.gateways.add(string2);
        }
        int int324 = hwBlob.getInt32(j + 104);
        HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 16, hwBlob.handle(), j + 96, true);
        this.pcscf.clear();
        for (int i6 = 0; i6 < int324; i6++) {
            new String();
            int i7 = i6 * 16;
            String string3 = readEmbeddedBuffer4.getString(i7);
            hwParcel.readEmbeddedBuffer(string3.getBytes().length + 1, readEmbeddedBuffer4.handle(), i7, false);
            this.pcscf.add(string3);
        }
        this.mtuV4 = hwBlob.getInt32(j + 112);
        this.mtuV6 = hwBlob.getInt32(j + 116);
        this.defaultQos.readEmbeddedFromParcel(hwParcel, hwBlob, j + 120);
        int int325 = hwBlob.getInt32(j + 160);
        HwBlob readEmbeddedBuffer5 = hwParcel.readEmbeddedBuffer(int325 * 48, hwBlob.handle(), j + 152, true);
        this.qosSessions.clear();
        for (int i8 = 0; i8 < int325; i8++) {
            QosSession qosSession = new QosSession();
            qosSession.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer5, i8 * 48);
            this.qosSessions.add(qosSession);
        }
        this.handoverFailureMode = hwBlob.getInt8(j + 168);
        this.pduSessionId = hwBlob.getInt32(j + 172);
        this.sliceInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 176);
        int int326 = hwBlob.getInt32(j + 208);
        HwBlob readEmbeddedBuffer6 = hwParcel.readEmbeddedBuffer(int326 * 48, hwBlob.handle(), j + 200, true);
        this.trafficDescriptors.clear();
        for (int i9 = 0; i9 < int326; i9++) {
            TrafficDescriptor trafficDescriptor = new TrafficDescriptor();
            trafficDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer6, i9 * 48);
            this.trafficDescriptors.add(trafficDescriptor);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(216);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SetupDataCallResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 216);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 216);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.cause);
        hwBlob.putInt64(8 + j, this.suggestedRetryTime);
        hwBlob.putInt32(16 + j, this.cid);
        hwBlob.putInt32(20 + j, this.active);
        hwBlob.putInt32(24 + j, this.type);
        hwBlob.putString(32 + j, this.ifname);
        int size = this.addresses.size();
        long j2 = 48 + j;
        hwBlob.putInt32(56 + j, size);
        hwBlob.putBool(60 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            this.addresses.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.dnses.size();
        long j3 = 64 + j;
        hwBlob.putInt32(72 + j, size2);
        hwBlob.putBool(76 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.dnses.get(i2));
        }
        hwBlob.putBlob(j3, hwBlob3);
        int size3 = this.gateways.size();
        long j4 = 80 + j;
        hwBlob.putInt32(88 + j, size3);
        hwBlob.putBool(92 + j, false);
        HwBlob hwBlob4 = new HwBlob(size3 * 16);
        for (int i3 = 0; i3 < size3; i3++) {
            hwBlob4.putString(i3 * 16, this.gateways.get(i3));
        }
        hwBlob.putBlob(j4, hwBlob4);
        int size4 = this.pcscf.size();
        long j5 = 96 + j;
        hwBlob.putInt32(104 + j, size4);
        hwBlob.putBool(108 + j, false);
        HwBlob hwBlob5 = new HwBlob(size4 * 16);
        for (int i4 = 0; i4 < size4; i4++) {
            hwBlob5.putString(i4 * 16, this.pcscf.get(i4));
        }
        hwBlob.putBlob(j5, hwBlob5);
        hwBlob.putInt32(112 + j, this.mtuV4);
        hwBlob.putInt32(116 + j, this.mtuV6);
        this.defaultQos.writeEmbeddedToBlob(hwBlob, 120 + j);
        int size5 = this.qosSessions.size();
        long j6 = 152 + j;
        hwBlob.putInt32(160 + j, size5);
        hwBlob.putBool(164 + j, false);
        HwBlob hwBlob6 = new HwBlob(size5 * 48);
        for (int i5 = 0; i5 < size5; i5++) {
            this.qosSessions.get(i5).writeEmbeddedToBlob(hwBlob6, i5 * 48);
        }
        hwBlob.putBlob(j6, hwBlob6);
        hwBlob.putInt8(168 + j, this.handoverFailureMode);
        hwBlob.putInt32(172 + j, this.pduSessionId);
        this.sliceInfo.writeEmbeddedToBlob(hwBlob, 176 + j);
        int size6 = this.trafficDescriptors.size();
        long j7 = 200 + j;
        hwBlob.putInt32(208 + j, size6);
        hwBlob.putBool(j + 212, false);
        HwBlob hwBlob7 = new HwBlob(size6 * 48);
        for (int i6 = 0; i6 < size6; i6++) {
            this.trafficDescriptors.get(i6).writeEmbeddedToBlob(hwBlob7, i6 * 48);
        }
        hwBlob.putBlob(j7, hwBlob7);
    }
}
