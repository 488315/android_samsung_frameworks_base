package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.ApnAuthType;
import android.hardware.radio.V1_0.DataProfileId;
import android.hardware.radio.V1_0.DataProfileInfoType;
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
    public int protocol = 0;
    public int roamingProtocol = 0;
    public int authType = 0;
    public String user = new String();
    public String password = new String();
    public int type = 0;
    public int maxConnsTime = 0;
    public int maxConns = 0;
    public int waitTime = 0;
    public boolean enabled = false;
    public int mtu = 0;
    public boolean preferred = false;
    public boolean persistent = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != DataProfileInfo.class) {
            return false;
        }
        DataProfileInfo dataProfileInfo = (DataProfileInfo) obj;
        return this.profileId == dataProfileInfo.profileId && HidlSupport.deepEquals(this.apn, dataProfileInfo.apn) && this.protocol == dataProfileInfo.protocol && this.roamingProtocol == dataProfileInfo.roamingProtocol && this.authType == dataProfileInfo.authType && HidlSupport.deepEquals(this.user, dataProfileInfo.user) && HidlSupport.deepEquals(this.password, dataProfileInfo.password) && this.type == dataProfileInfo.type && this.maxConnsTime == dataProfileInfo.maxConnsTime && this.maxConns == dataProfileInfo.maxConns && this.waitTime == dataProfileInfo.waitTime && this.enabled == dataProfileInfo.enabled && HidlSupport.deepEquals(Integer.valueOf(this.supportedApnTypesBitmap), Integer.valueOf(dataProfileInfo.supportedApnTypesBitmap)) && HidlSupport.deepEquals(Integer.valueOf(this.bearerBitmap), Integer.valueOf(dataProfileInfo.bearerBitmap)) && this.mtu == dataProfileInfo.mtu && this.preferred == dataProfileInfo.preferred && this.persistent == dataProfileInfo.persistent;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.profileId))), Integer.valueOf(HidlSupport.deepHashCode(this.apn)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.protocol))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.roamingProtocol))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.authType))), Integer.valueOf(HidlSupport.deepHashCode(this.user)), Integer.valueOf(HidlSupport.deepHashCode(this.password)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxConnsTime))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxConns))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.waitTime))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.enabled))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.supportedApnTypesBitmap))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.bearerBitmap))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtu))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.preferred))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.persistent))));
    }

    public final String toString() {
        return "{.profileId = " + DataProfileId.toString(this.profileId) + ", .apn = " + this.apn + ", .protocol = " + PdpProtocolType.toString(this.protocol) + ", .roamingProtocol = " + PdpProtocolType.toString(this.roamingProtocol) + ", .authType = " + ApnAuthType.toString(this.authType) + ", .user = " + this.user + ", .password = " + this.password + ", .type = " + DataProfileInfoType.toString(this.type) + ", .maxConnsTime = " + this.maxConnsTime + ", .maxConns = " + this.maxConns + ", .waitTime = " + this.waitTime + ", .enabled = " + this.enabled + ", .supportedApnTypesBitmap = " + ApnTypes.dumpBitfield(this.supportedApnTypesBitmap) + ", .bearerBitmap = " + RadioAccessFamily.dumpBitfield(this.bearerBitmap) + ", .mtu = " + this.mtu + ", .preferred = " + this.preferred + ", .persistent = " + this.persistent + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final ArrayList<DataProfileInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataProfileInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            DataProfileInfo dataProfileInfo = new DataProfileInfo();
            dataProfileInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 112);
            arrayList.add(dataProfileInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.profileId = hwBlob.getInt32(j);
        long j2 = j + 8;
        this.apn = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.protocol = hwBlob.getInt32(j + 24);
        this.roamingProtocol = hwBlob.getInt32(j + 28);
        this.authType = hwBlob.getInt32(j + 32);
        long j3 = j + 40;
        this.user = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        long j4 = j + 56;
        this.password = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j4, false);
        this.type = hwBlob.getInt32(j + 72);
        this.maxConnsTime = hwBlob.getInt32(j + 76);
        this.maxConns = hwBlob.getInt32(j + 80);
        this.waitTime = hwBlob.getInt32(j + 84);
        this.enabled = hwBlob.getBool(j + 88);
        this.supportedApnTypesBitmap = hwBlob.getInt32(j + 92);
        this.bearerBitmap = hwBlob.getInt32(j + 96);
        this.mtu = hwBlob.getInt32(j + 100);
        this.preferred = hwBlob.getBool(j + 104);
        this.persistent = hwBlob.getBool(j + 105);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataProfileInfo> arrayList) {
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
        hwBlob.putInt32(j, this.profileId);
        hwBlob.putString(8 + j, this.apn);
        hwBlob.putInt32(24 + j, this.protocol);
        hwBlob.putInt32(28 + j, this.roamingProtocol);
        hwBlob.putInt32(32 + j, this.authType);
        hwBlob.putString(40 + j, this.user);
        hwBlob.putString(56 + j, this.password);
        hwBlob.putInt32(72 + j, this.type);
        hwBlob.putInt32(76 + j, this.maxConnsTime);
        hwBlob.putInt32(80 + j, this.maxConns);
        hwBlob.putInt32(84 + j, this.waitTime);
        hwBlob.putBool(88 + j, this.enabled);
        hwBlob.putInt32(92 + j, this.supportedApnTypesBitmap);
        hwBlob.putInt32(96 + j, this.bearerBitmap);
        hwBlob.putInt32(100 + j, this.mtu);
        hwBlob.putBool(104 + j, this.preferred);
        hwBlob.putBool(j + 105, this.persistent);
    }
}
