package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehCsgInfo {
    public int csgId = 0;
    public String name = new String();
    public String plmn = new String();
    public int rat = 0;
    public int category = 0;
    public int signalStrength = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehCsgInfo.class) {
            return false;
        }
        SehCsgInfo other = (SehCsgInfo) otherObject;
        if (this.csgId == other.csgId && HidlSupport.deepEquals(this.name, other.name) && HidlSupport.deepEquals(this.plmn, other.plmn) && this.rat == other.rat && this.category == other.category && this.signalStrength == other.signalStrength) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csgId))), Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(this.plmn)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.category))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalStrength))));
    }

    public final String toString() {
        return "{.csgId = " + this.csgId + ", .name = " + this.name + ", .plmn = " + this.plmn + ", .rat = " + this.rat + ", .category = " + this.category + ", .signalStrength = " + this.signalStrength + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(56L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehCsgInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehCsgInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 56, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehCsgInfo _hidl_vec_element = new SehCsgInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 56);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.csgId = _hidl_blob.getInt32(_hidl_offset + 0);
        this.name = _hidl_blob.getString(_hidl_offset + 8);
        parcel.readEmbeddedBuffer(this.name.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        this.plmn = _hidl_blob.getString(_hidl_offset + 24);
        parcel.readEmbeddedBuffer(this.plmn.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 24 + 0, false);
        this.rat = _hidl_blob.getInt32(_hidl_offset + 40);
        this.category = _hidl_blob.getInt32(_hidl_offset + 44);
        this.signalStrength = _hidl_blob.getInt32(_hidl_offset + 48);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(56);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SehCsgInfo> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 56);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 56);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.csgId);
        _hidl_blob.putString(8 + _hidl_offset, this.name);
        _hidl_blob.putString(24 + _hidl_offset, this.plmn);
        _hidl_blob.putInt32(40 + _hidl_offset, this.rat);
        _hidl_blob.putInt32(44 + _hidl_offset, this.category);
        _hidl_blob.putInt32(48 + _hidl_offset, this.signalStrength);
    }
}
