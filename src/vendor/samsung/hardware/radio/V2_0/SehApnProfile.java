package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehApnProfile {
    public String apn = new String();
    public String proto = new String();
    public String roamingProto = new String();
    public String user = new String();
    public String pw = new String();
    public String auth = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SehApnProfile.class) {
            return false;
        }
        SehApnProfile sehApnProfile = (SehApnProfile) obj;
        return HidlSupport.deepEquals(this.apn, sehApnProfile.apn) && HidlSupport.deepEquals(this.proto, sehApnProfile.proto) && HidlSupport.deepEquals(this.roamingProto, sehApnProfile.roamingProto) && HidlSupport.deepEquals(this.user, sehApnProfile.user) && HidlSupport.deepEquals(this.pw, sehApnProfile.pw) && HidlSupport.deepEquals(this.auth, sehApnProfile.auth);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.apn)), Integer.valueOf(HidlSupport.deepHashCode(this.proto)), Integer.valueOf(HidlSupport.deepHashCode(this.roamingProto)), Integer.valueOf(HidlSupport.deepHashCode(this.user)), Integer.valueOf(HidlSupport.deepHashCode(this.pw)), Integer.valueOf(HidlSupport.deepHashCode(this.auth)));
    }

    public final String toString() {
        return "{.apn = " + this.apn + ", .proto = " + this.proto + ", .roamingProto = " + this.roamingProto + ", .user = " + this.user + ", .pw = " + this.pw + ", .auth = " + this.auth + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final ArrayList<SehApnProfile> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SehApnProfile> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SehApnProfile sehApnProfile = new SehApnProfile();
            sehApnProfile.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 96);
            arrayList.add(sehApnProfile);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.apn = hwBlob.getString(j);
        hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
        long j2 = j + 16;
        this.proto = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
        long j3 = j + 32;
        this.roamingProto = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
        long j4 = j + 48;
        this.user = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j4, false);
        long j5 = j + 64;
        this.pw = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j5, false);
        long j6 = j + 80;
        this.auth = hwBlob.getString(j6);
        hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j6, false);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SehApnProfile> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(j, this.apn);
        hwBlob.putString(16 + j, this.proto);
        hwBlob.putString(32 + j, this.roamingProto);
        hwBlob.putString(48 + j, this.user);
        hwBlob.putString(64 + j, this.pw);
        hwBlob.putString(j + 80, this.auth);
    }
}
