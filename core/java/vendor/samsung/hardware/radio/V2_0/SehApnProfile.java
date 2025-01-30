package vendor.samsung.hardware.radio.V2_0;

import android.p009os.HidlSupport;
import android.p009os.HwBlob;
import android.p009os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehApnProfile {
    public String apn = new String();
    public String proto = new String();
    public String roamingProto = new String();
    public String user = new String();

    /* renamed from: pw */
    public String f3119pw = new String();
    public String auth = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehApnProfile.class) {
            return false;
        }
        SehApnProfile other = (SehApnProfile) otherObject;
        if (HidlSupport.deepEquals(this.apn, other.apn) && HidlSupport.deepEquals(this.proto, other.proto) && HidlSupport.deepEquals(this.roamingProto, other.roamingProto) && HidlSupport.deepEquals(this.user, other.user) && HidlSupport.deepEquals(this.f3119pw, other.f3119pw) && HidlSupport.deepEquals(this.auth, other.auth)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.apn)), Integer.valueOf(HidlSupport.deepHashCode(this.proto)), Integer.valueOf(HidlSupport.deepHashCode(this.roamingProto)), Integer.valueOf(HidlSupport.deepHashCode(this.user)), Integer.valueOf(HidlSupport.deepHashCode(this.f3119pw)), Integer.valueOf(HidlSupport.deepHashCode(this.auth)));
    }

    public final String toString() {
        return "{.apn = " + this.apn + ", .proto = " + this.proto + ", .roamingProto = " + this.roamingProto + ", .user = " + this.user + ", .pw = " + this.f3119pw + ", .auth = " + this.auth + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(96L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehApnProfile> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehApnProfile> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 96, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehApnProfile _hidl_vec_element = new SehApnProfile();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 96);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.apn = _hidl_blob.getString(_hidl_offset + 0);
        parcel.readEmbeddedBuffer(r4.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 0 + 0, false);
        this.proto = _hidl_blob.getString(_hidl_offset + 16);
        parcel.readEmbeddedBuffer(r6.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 16 + 0, false);
        this.roamingProto = _hidl_blob.getString(_hidl_offset + 32);
        parcel.readEmbeddedBuffer(r6.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 32 + 0, false);
        this.user = _hidl_blob.getString(_hidl_offset + 48);
        parcel.readEmbeddedBuffer(r6.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 48 + 0, false);
        this.f3119pw = _hidl_blob.getString(_hidl_offset + 64);
        parcel.readEmbeddedBuffer(r6.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 64 + 0, false);
        this.auth = _hidl_blob.getString(_hidl_offset + 80);
        parcel.readEmbeddedBuffer(r6.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 80 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(96);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SehApnProfile> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 96);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 96);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putString(0 + _hidl_offset, this.apn);
        _hidl_blob.putString(16 + _hidl_offset, this.proto);
        _hidl_blob.putString(32 + _hidl_offset, this.roamingProto);
        _hidl_blob.putString(48 + _hidl_offset, this.user);
        _hidl_blob.putString(64 + _hidl_offset, this.f3119pw);
        _hidl_blob.putString(80 + _hidl_offset, this.auth);
    }
}
