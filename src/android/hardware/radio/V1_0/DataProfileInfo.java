package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DataProfileInfo {
    public int bearerBitmap;
    public int supportedApnTypesBitmap;
    public int profileId = 0;
    public String apn = new String();
    public String protocol = new String();
    public String roamingProtocol = new String();
    public int authType = 0;
    public String user = new String();
    public String password = new String();
    public int type = 0;
    public int maxConnsTime = 0;
    public int maxConns = 0;
    public int waitTime = 0;
    public boolean enabled = false;
    public int mtu = 0;
    public int mvnoType = 0;
    public String mvnoMatchData = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != DataProfileInfo.class) {
            return false;
        }
        DataProfileInfo dataProfileInfo = (DataProfileInfo) obj;
        return this.profileId == dataProfileInfo.profileId && HidlSupport.deepEquals(this.apn, dataProfileInfo.apn) && HidlSupport.deepEquals(this.protocol, dataProfileInfo.protocol) && HidlSupport.deepEquals(this.roamingProtocol, dataProfileInfo.roamingProtocol) && this.authType == dataProfileInfo.authType && HidlSupport.deepEquals(this.user, dataProfileInfo.user) && HidlSupport.deepEquals(this.password, dataProfileInfo.password) && this.type == dataProfileInfo.type && this.maxConnsTime == dataProfileInfo.maxConnsTime && this.maxConns == dataProfileInfo.maxConns && this.waitTime == dataProfileInfo.waitTime && this.enabled == dataProfileInfo.enabled && HidlSupport.deepEquals(Integer.valueOf(this.supportedApnTypesBitmap), Integer.valueOf(dataProfileInfo.supportedApnTypesBitmap)) && HidlSupport.deepEquals(Integer.valueOf(this.bearerBitmap), Integer.valueOf(dataProfileInfo.bearerBitmap)) && this.mtu == dataProfileInfo.mtu && this.mvnoType == dataProfileInfo.mvnoType && HidlSupport.deepEquals(this.mvnoMatchData, dataProfileInfo.mvnoMatchData);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.profileId))), Integer.valueOf(HidlSupport.deepHashCode(this.apn)), Integer.valueOf(HidlSupport.deepHashCode(this.protocol)), Integer.valueOf(HidlSupport.deepHashCode(this.roamingProtocol)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.authType))), Integer.valueOf(HidlSupport.deepHashCode(this.user)), Integer.valueOf(HidlSupport.deepHashCode(this.password)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxConnsTime))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxConns))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.waitTime))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.enabled))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.supportedApnTypesBitmap))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.bearerBitmap))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtu))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mvnoType))), Integer.valueOf(HidlSupport.deepHashCode(this.mvnoMatchData)));
    }

    public final String toString() {
        return "{.profileId = " + DataProfileId.toString(this.profileId) + ", .apn = " + this.apn + ", .protocol = " + this.protocol + ", .roamingProtocol = " + this.roamingProtocol + ", .authType = " + ApnAuthType.toString(this.authType) + ", .user = " + this.user + ", .password = " + this.password + ", .type = " + DataProfileInfoType.toString(this.type) + ", .maxConnsTime = " + this.maxConnsTime + ", .maxConns = " + this.maxConns + ", .waitTime = " + this.waitTime + ", .enabled = " + this.enabled + ", .supportedApnTypesBitmap = " + ApnTypes.dumpBitfield(this.supportedApnTypesBitmap) + ", .bearerBitmap = " + RadioAccessFamily.dumpBitfield(this.bearerBitmap) + ", .mtu = " + this.mtu + ", .mvnoType = " + MvnoType.toString(this.mvnoType) + ", .mvnoMatchData = " + this.mvnoMatchData + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(152L), 0L);
    }

    public static final ArrayList<DataProfileInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataProfileInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 152, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            DataProfileInfo dataProfileInfo = new DataProfileInfo();
            dataProfileInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 152);
            arrayList.add(dataProfileInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.profileId = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.apn = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 24;
        this.protocol = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        long j4 = j + 40;
        this.roamingProtocol = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j4, false);
        this.authType = hwBlob.getInt32(j + 56);
        long j5 = j + 64;
        this.user = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j5, false);
        long j6 = j + 80;
        this.password = hwBlob.getString(j6);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j6, false);
        this.type = hwBlob.getInt32(j + 96);
        this.maxConnsTime = hwBlob.getInt32(j + 100);
        this.maxConns = hwBlob.getInt32(j + 104);
        this.waitTime = hwBlob.getInt32(j + 108);
        this.enabled = hwBlob.getBool(j + 112);
        this.supportedApnTypesBitmap = hwBlob.getInt32(j + 116);
        this.bearerBitmap = hwBlob.getInt32(j + 120);
        this.mtu = hwBlob.getInt32(j + 124);
        this.mvnoType = hwBlob.getInt32(j + 128);
        long j7 = j + 136;
        this.mvnoMatchData = hwBlob.getString(j7);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j7, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(152);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataProfileInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 152);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 152);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.profileId);
        hwBlob.putString(8 + j, this.apn);
        hwBlob.putString(24 + j, this.protocol);
        hwBlob.putString(40 + j, this.roamingProtocol);
        hwBlob.putInt32(56 + j, this.authType);
        hwBlob.putString(64 + j, this.user);
        hwBlob.putString(80 + j, this.password);
        hwBlob.putInt32(96 + j, this.type);
        hwBlob.putInt32(100 + j, this.maxConnsTime);
        hwBlob.putInt32(104 + j, this.maxConns);
        hwBlob.putInt32(108 + j, this.waitTime);
        hwBlob.putBool(112 + j, this.enabled);
        hwBlob.putInt32(116 + j, this.supportedApnTypesBitmap);
        hwBlob.putInt32(120 + j, this.bearerBitmap);
        hwBlob.putInt32(124 + j, this.mtu);
        hwBlob.putInt32(128 + j, this.mvnoType);
        hwBlob.putString(j + 136, this.mvnoMatchData);
    }
}
